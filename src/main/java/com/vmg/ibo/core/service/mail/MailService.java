package com.vmg.ibo.core.service.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.function.Function;


@Service
public class MailService implements IMailService {

    private final JavaMailSender emailSender;

    private final String sender;

    public MailService(JavaMailSender emailSender, @Value("${spring.mail.username}") String sender) {
        this.emailSender = emailSender;
        this.sender = sender;
    }

    @Async
    public void sendFromSystem(Function<MailMessage, SimpleMailMessage> message) {
        emailSender.send(message.apply(MailMessage.from(this.sender)));
    }

    @Async
    public void sendWithAttachment(Function<MailMessage, MimeMessage> message) {
        emailSender.send(message.apply(MailMessage.from(this.sender)));
    }
}
