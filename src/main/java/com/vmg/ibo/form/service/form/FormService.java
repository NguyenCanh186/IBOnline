package com.vmg.ibo.form.service.form;

import com.vmg.ibo.core.base.BaseFilter;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.MailMessageConstant;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.core.service.mail.IMailService;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.repository.IUserDetailRepository;
import com.vmg.ibo.form.dto.DemandDTO;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.dto.FormFieldDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
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

    @Override
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    @Override
    public FormDTO getFormById(Long id) {
        userService.checkChannel(getCurrentUser());
        Optional<Form> form = formRepository.findById(id);
        List<Long> list = new ArrayList<>();
        if (form.isPresent()) {
            List<Template> templateList;
            if (form.get().getTemplate().getType() == 2) {
                templateList = templateRepository.findAllByType(1);
            } else {
                templateList = templateRepository.findAllByType(2);
            }
            String tag = form.get().getTemplate().getTag();
            for (Template template : templateList) {
                String tagTemplate =  template.getTag();
                if (tag.toLowerCase().contains(tagTemplate.toLowerCase()) || tagTemplate.toLowerCase().contains(tag.toLowerCase())) {
                    list.add(template.getId());
                }
            }
            List<Form> listSuggestLatest = new ArrayList<>();
            if (form.get().getPartnerId() == null) {
                listSuggestLatest = formRepository.findTop3ByTemplateIdInAndUserIdNotAndPartnerIdNullOrderByCreatedAtDesc(list, getCurrentUser().getId());
            } else {
                Optional<Form> findParent = formRepository.findById(form.get().getPartnerId());
                if (findParent.isPresent()) {
                    listSuggestLatest.add(findParent.get());

                }
            }
            ModelMapper modelMapper = new ModelMapper();
            FormDTO formDTO = modelMapper.map(form.get(), FormDTO.class);
            formDTO.setSuggestLatest(
                    listSuggestLatest.stream()
                            .map(x -> modelMapper.map(x, FormSuggestDTO.class))
                            .peek(FormSuggestDTO::getFormFields) // Thực hiện getFilteredFormFields trên mỗi phần tử
                            .collect(Collectors.toList())
            );
            Long userID = form.get().getUser().getId();
            UserDetail userDetail = userDetailRepository.findByIdUser(userID);
            formDTO.getUser().setUserDetail(userDetail);
            formDTO.setCodeDemand(form.get().getCodeDemand());
            return formDTO;
        } else {
            throw new WebServiceException(HttpStatus.OK.value(),409, "Không tìm thấy nhu cầu hợp lệ");
        }
    }




    @Override
    public Form createForm(Form form) {
        return formRepository.save(form);
    }

    @Override
    public List<String> getAllCodeDemand() {
        return formRepository.getAllCodeDemand();
    }


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
            sendEmailsForStatus1(formPartner);
            return form;
        }
    }


    @Override
    public Page<DemandDTO> getAllDemand(DemandReq demandReq, Pageable pageable) {
        if (Objects.isNull(demandReq.getDemandType())) {
            demandReq.setDemandType(new ArrayList<>());
            demandReq.getDemandType().add(1);
            demandReq.getDemandType().add(2);
        }
        if (Objects.isNull(demandReq.getStatus())) {
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
            if(user.getChannelId() == 2) {
                userId = null;
            }
        }
        return formRepository.getAllDemand(demandReq,userId, pageable);
    }

    @Override
    public Page<Form> getAllFormByUser(Long id, BaseFilter filter) {
        User user = userRepository.findById(id).orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(),409, "Không tìm thấy người dùng"));
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(filter, sort);
        return formRepository.findByUser(user, pageable);
    }

    private void sendEmailsForStatus1(Form formPartner) {
        User userParent = formPartner.getUser();
        List<User> users = userRepository.findUsersByRoleId(13L);
        List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
        emailService.sendEmail(emails.get(0), MailMessageConstant.DEMAND, "Xin chào bạn \n" +
                "Hệ thống IBOnline ghi nhận " + getCurrentUser().getName() +
                " đang có nhu cầu kết nối đến nhu cầu " + formPartner.getCodeDemand() +
                " của khách hàng " + userParent.getName() + ". " +
                "Bạn có thể xem chi tiết tại đây:url\n", emails);

    }
}
