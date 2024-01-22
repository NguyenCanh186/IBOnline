package com.vmg.ibo.form.service.form;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.MailMessageConstant;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.core.service.mail.IMailService;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.form.dto.FormDTO;
import com.vmg.ibo.form.dto.FormSuggestDTO;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.Template;
import com.vmg.ibo.form.model.FormUpdateStatusReq;
import com.vmg.ibo.form.repository.IFormRepository;
import com.vmg.ibo.form.repository.TemplateRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            for (Template template : templateList) {
                if (form.get().getTemplate().getTag().contains(template.getTag())) {
                    list.add(template.getId());
                }
            }
            List<Form> listSuggestLatest = new ArrayList<>();
            if(form.get().getPartnerId() == null) {
                listSuggestLatest  = formRepository.findTop3ByTemplateIdInAndUserIdNotOrderByCreatedAtDesc(list, getCurrentUser().getId());
            } else {
                Optional<Form> findParent = formRepository.findById(form.get().getPartnerId());
                if(findParent.isPresent()) {
                    listSuggestLatest.add(findParent.get());

                }
            }
            ModelMapper modelMapper = new ModelMapper();
            FormDTO formDTO = modelMapper.map(form.get(), FormDTO.class);
            formDTO.setSuggestLatest(listSuggestLatest.stream().map(x -> modelMapper.map(x, FormSuggestDTO.class)).collect(Collectors.toList()));
            return formDTO;
        } else {
            throw new WebServiceException(HttpStatus.OK.value(), "Không tìm thấy nhu cầu hợp lệ");
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


    public Form updateStatus(Long id, FormUpdateStatusReq formUpdateStatusReq) {
        userService.checkChannel(getCurrentUser());
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new WebServiceException(HttpStatus.OK.value(), "Không tìm thấy nhu cầu hợp lệ"));
        form.setStatus(formUpdateStatusReq.getStatus());
        if (formUpdateStatusReq.getStatus() == 1) {
            sendEmailsForStatus1(form, formUpdateStatusReq.getPartnerId());
        }
        form.setPartnerId(formUpdateStatusReq.getPartnerId());
        return formRepository.save(form);
    }

    private void sendEmailsForStatus1(Form form, Long parentId) {
        Optional<Form> formParent = formRepository.findById(parentId);
        if (formParent.isPresent()) {
            User userParent = formParent.get().getUser();
            List<User> users = userRepository.findUsersByRoleId(13L);
            users.forEach(user -> {
                iMailService.sendFromSystem(message -> message.to(user.getEmail())
                        .subject(MailMessageConstant.DEMAND)
                        .text("Xin chào " + user.getName() + "\n" +
                                "Hệ thống IBOnline ghi nhận " + getCurrentUser().getName() +
                                " đang có nhu cầu kết nối đến nhu cầu " + formParent.get().getCodeDemand() +
                                " của khách hàng " + userParent.getName() + ". " +
                                "Bạn có thể xem chi tiết tại đây:url\n")
                        .build());
            });
        } else {
            throw new WebServiceException(HttpStatus.OK.value(), "Không tìm thấy nhu cầu hợp lệ");
        }
    }
}
