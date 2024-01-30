package com.vmg.ibo.form.service.form;

import com.vmg.ibo.core.base.BaseFilter;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.MailMessageConstant;
import com.vmg.ibo.core.constant.UserConstant;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.core.service.mail.IMailService;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.repository.IUserDetailRepository;
import com.vmg.ibo.deal.constant.DealConstant;
import com.vmg.ibo.deal.model.entity.Deal;
import com.vmg.ibo.deal.service.IDealService;
import com.vmg.ibo.form.dto.DemandDTO;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.dto.FormSuggestDTO;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.model.DemandReq;
import com.vmg.ibo.form.model.FormUpdateStatusReq;
import com.vmg.ibo.form.repository.IFormRepository;
import com.vmg.ibo.form.repository.TemplateRepository;
import com.vmg.ibo.form.service.email.EmailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormService extends BaseService implements IFormService {
    private static final Logger logger = LoggerFactory.getLogger(IFormService.class);

    @Autowired
    private IFormRepository formRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private IMailService iMailService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IUserDetailRepository userDetailRepository;

    @Autowired
    private IDealService dealService;

    @Value("${cms.url}")
    private String cmsUrl;

    @Override
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    @Override
    public FormDTO getFormById(Long id) {
        userService.checkChannel(getCurrentUser());
        Optional<Form> formOptional = formRepository.findById(id);

        Form form = formOptional.orElseThrow(() ->
                new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy nhu cầu hợp lệ"));

        validateCurrentUser(form);

        List<Template> templateList = getTemplateList(form);

        String tag = form.getTemplate().getTag();
        List<Long> list = templateList.stream()
                .filter(template -> tagMatches(tag, template.getTag()))
                .map(Template::getId)
                .collect(Collectors.toList());

        List<Form> listSuggestLatest = getListSuggestLatest(form, list);

        ModelMapper modelMapper = new ModelMapper();
        FormDTO formDTO = modelMapper.map(form, FormDTO.class);
        formDTO.setSuggestLatest(
                listSuggestLatest.stream()
                        .map(x -> modelMapper.map(x, FormSuggestDTO.class))
                        .peek(FormSuggestDTO::getFormFields)
                        .collect(Collectors.toList())
        );

        setUserDetailsAndCodeDemand(formDTO, form);

        return formDTO;
    }

    @Override
    public FormDTO getFormCMSById(Long id) {
        Optional<Form> formOptional = formRepository.findById(id);
        Form form = formOptional.orElseThrow(() ->
                new WebServiceException(HttpStatus.OK.value(), 409, "Không tìm thấy nhu cầu hợp lệ"));
        List<Template> templateList = getTemplateList(form);
        String tag = form.getTemplate().getTag();
        List<Long> list = templateList.stream()
                .filter(template -> tagMatches(tag, template.getTag()))
                .map(Template::getId)
                .collect(Collectors.toList());
        List<Form> listSuggestLatest = getListSuggestLatest(form, list);
        ModelMapper modelMapper = new ModelMapper();
        FormDTO formDTO = modelMapper.map(form, FormDTO.class);
        formDTO.setSuggestLatest(
                listSuggestLatest.stream()
                        .map(x -> modelMapper.map(x, FormSuggestDTO.class))
                        .peek(FormSuggestDTO::getFormFields)
                        .collect(Collectors.toList())
        );
        setUserDetailsAndCodeDemand(formDTO, form);
        return formDTO;
    }


    private void validateCurrentUser(Form form) {
        if (!getCurrentUser().getId().equals(form.getUser().getId())) {
            throw new WebServiceException(HttpStatus.OK.value(), 409, "Tài khoản không có quyền xem chi tiết nhu cầu");
        }
    }

    private List<Template> getTemplateList(Form form) {
        Template template = form.getTemplate();
        int templateType = template.getType();
        long templateId = template.getId();

        switch (templateType) {
            case 2:
                return (templateId == 11) ?
                        templateRepository.findAllByTypeAndIdIn(1, Arrays.asList(3L, 4L, 5L, 6L, 7L, 8L)) :
                        templateRepository.findAllByTypeAndIdIn(1, Collections.singletonList(2L));

            default:
                return (templateId == 2) ?
                        templateRepository.findAllByTypeAndIdIn(2, Arrays.asList(1L, 9L, 10L)) :
                        templateRepository.findAllByTypeAndIdIn(2, Collections.singletonList(11L));
        }
    }

    private boolean tagMatches(String tag, String tagTemplate) {
        return tag.toLowerCase().contains(tagTemplate.toLowerCase()) || tagTemplate.toLowerCase().contains(tag.toLowerCase());
    }

    private List<Form> getListSuggestLatest(Form form, List<Long> list) {
        if (form.getPartnerId() == null) {
            return formRepository.findTop3ByTemplateIdInAndUserIdNotAndPartnerIdNullOrderByCreatedAtDesc(list, getCurrentUser().getId());
        } else {
            Optional<Form> findParent = formRepository.findById(form.getPartnerId());
            return findParent.map(Collections::singletonList).orElseGet(ArrayList::new);
        }
    }

    private void setUserDetailsAndCodeDemand(FormDTO formDTO, Form form) {
        Long userID = form.getUser().getId();
        UserDetail userDetail = userDetailRepository.findByIdUser(userID);
        formDTO.getUser().setUserDetail(userDetail);
        formDTO.setCodeDemand(form.getCodeDemand());
    }




    @Override
    public Form createForm(Form form) {
        return formRepository.save(form);
    }

    @Override
    public List<String> getAllCodeDemand() {
        return formRepository.getAllCodeDemand();
    }


    @Override
    @Transactional
    public Form connect(Long id, FormUpdateStatusReq formUpdateStatusReq) {
        userService.checkChannel(getCurrentUser());
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(),HttpStatus.CONFLICT.value(), "Không tìm thấy nhu cầu hợp lệ"));
        Form formPartner = formRepository.findById(formUpdateStatusReq.getPartnerId())
                .orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(),HttpStatus.CONFLICT.value(), "Không tìm thấy nhu cầu hợp lệ"));
        if (form.getPartnerId() != null) {
            throw new WebServiceException(HttpStatus.OK.value(),HttpStatus.CONFLICT.value(), "Nhu cầu đã được kết nối");
        } else if (formPartner.getPartnerId() != null) {
            throw new WebServiceException(HttpStatus.OK.value(),HttpStatus.CONFLICT.value(), "Đối tác đã được kết nối vui lòng chọn đối tác khác");
        }
        else {
            form.setStatus(1);
            form.setPartnerId(formUpdateStatusReq.getPartnerId());
            form = formRepository.save(form);
            formPartner.setStatus(1);
            formPartner.setPartnerId(id);
            formRepository.save(formPartner);
            Deal deal = createDeal(form, formPartner);
            sendEmailsForStatus1(formPartner,deal);

            return form;
        }
    }

    private Deal createDeal(Form first, Form second) {
        Deal deal = new Deal();
        deal.setFirst(first);
        deal.setSecond(second);
        deal.setConnectionDate(new Date());
        deal.setStatus(Integer.parseInt(DealConstant.PROCESSING.getValue()));
        return dealService.save(deal);
    }


    @Override
    public Page<DemandDTO> getAllDemand(DemandReq demandReq, Pageable pageable) {
        if (demandReq.getDemandType() == null || demandReq.getDemandType().isEmpty()) {
            demandReq.setDemandType(new ArrayList<>());
            demandReq.getDemandType().add(1);
            demandReq.getDemandType().add(2);
        }
        if (demandReq.getStatus() == null ||  demandReq.getStatus().isEmpty()) {
            demandReq.setStatus(new ArrayList<>());
            demandReq.getStatus().add(0);
            demandReq.getStatus().add(1);
            demandReq.getStatus().add(2);
            demandReq.getStatus().add(3);
        }
        Long userId = getCurrentUser().getId();
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getChannelId() == UserConstant.CHANNEL_ADMIN.getValue()) {
                userId = null;
            }
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return formRepository.getAllDemand(demandReq,userId, pageable);
    }

    @Override
    public Page<Form> getAllFormByUser(Long id, BaseFilter filter) {
        User user = userRepository.findById(id).orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(),409, "Không tìm thấy người dùng"));
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(filter, sort);
        return formRepository.findByUser(user, pageable);
    }

    private void sendEmailsForStatus1(Form formPartner,Deal deal) {
        User userParent = formPartner.getUser();
        List<User> users = userRepository.findUsersByRoleId(13L);
        if(!Objects.isNull(users) && !users.isEmpty()) {
            List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
            String url = cmsUrl + "/cms/quan-ly-deal/" + deal.getId();
            emailService.sendEmail(emails.get(0), MailMessageConstant.DEMAND, "Xin chào bạn \n" +
                    "Hệ thống IBOnline ghi nhận " + getCurrentUser().getName() +
                    " đang có nhu cầu kết nối đến nhu cầu " + formPartner.getCodeDemand() +
                    " của khách hàng " + userParent.getName() + ".\n " +
                    "Bạn có thể xem chi tiết tại đây:" + url, emails);
        } else {
            throw new WebServiceException(HttpStatus.OK.value(),409, "Không tìm thấy điểu phối viên");
        }
    }
}
