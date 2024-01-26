package com.vmg.ibo.contact_form.service;

import com.vmg.ibo.contact_form.model.dto.ContactFormDTO;
import com.vmg.ibo.contact_form.model.entity.ContactForm;
import com.vmg.ibo.contact_form.repository.IContactFormRepository;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.MailMessageConstant;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.form.service.email.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ContactFormService extends BaseService implements IContactFormService {
    private final IContactFormRepository contactFormRepository;

    private final IUserRepository userRepository;

    private final EmailService emailService;

    public ContactFormService(IContactFormRepository contactFormRepository, IUserRepository userRepository, EmailService emailService) {
        this.contactFormRepository = contactFormRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public ContactFormDTO create(ContactFormDTO contactFormDTO) {
        ContactForm contactForm = mapToEntity(contactFormDTO);
        contactForm.setCreatedAt(new Date());
        contactForm.setCreatedBy("SYSTEM");
        contactForm = contactFormRepository.save(contactForm);
        sendEmailToCoordinator(contactForm);
        return mapToDTO(contactForm);
    }

    private void sendEmailToCoordinator(ContactForm contactForm) {
        List<User> users = userRepository.findUsersByRoleId(13L);
        if(!Objects.isNull(users) && !users.isEmpty()) {
            List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
            StringBuilder message = new StringBuilder("Xin chào, Hệ thống IBOnline nhận được yêu cầu liên hệ từ \n");
            message.append("Họ tên: ").append(contactForm.getFullname()).append("\n");
            message.append("Số điện thoại: ").append(contactForm.getPhone()).append("\n");
            if (!Objects.isNull(contactForm.getEmail())) {
                message.append("Email: ").append(contactForm.getEmail()).append("\n");
            }
            if (!Objects.isNull(contactForm.getDescription())) {
                message.append("Nội dung yêu cầu: ").append(contactForm.getDescription()).append("\n");
            }
            emailService.sendEmail(emails.get(0), String.format(MailMessageConstant.CONTACT_FORM_SUBJECT, contactForm.getFullname()), message.toString(), emails);
        } else {
            throw new WebServiceException(HttpStatus.OK.value(),409, "Không tìm thấy điểu phối viên");
        }
    }

    private ContactForm mapToEntity(ContactFormDTO contactFormDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(contactFormDTO, ContactForm.class);
    }

    private ContactFormDTO mapToDTO(ContactForm contactForm) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(contactForm, ContactFormDTO.class);
    }
}
