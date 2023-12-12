package com.vmg.ibo.core.service.mail;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;
import java.util.function.Function;

public interface IMailService {
    void sendFromSystem(Function<MailMessage, SimpleMailMessage> message);

    void sendWithAttachment(Function<MailMessage, MimeMessage> message);
}
