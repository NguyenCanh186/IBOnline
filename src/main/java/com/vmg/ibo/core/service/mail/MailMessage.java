package com.vmg.ibo.core.service.mail;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Base64;

public class MailMessage {

    private String from;

    private String to;

    private String subject;

    private String text;

    private MailMessage(String from) {
        this.from = from;
    }

    private MailMessage() {
    }

    public static MailMessage fromSystem() {
        return new MailMessage();
    }

    public static MailMessage from(String from) {
        return new MailMessage(from);
    }

    public MailMessage to(String to) {
        this.to = to;
        return this;
    }

    public MailMessage subject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailMessage text(String text) {
        this.text = text;
        return this;
    }

    public SimpleMailMessage build() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(this.to);
        message.setSubject(this.subject);
        message.setText(this.text);
        return message;
    }

    public MimeMessage buildMailMessageWithAttachmentFromBase64(MimeMessage mimeMessage, String base64Data) {
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setFrom(this.from);
            message.setTo(this.to);
            message.setSubject(this.subject);
            message.setText(this.text);
            message.addAttachment("qrcode.png",
                    new ByteArrayResource(Base64.getDecoder().decode(base64Data.replace("data:image/png;base64,", ""))));
            return mimeMessage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
