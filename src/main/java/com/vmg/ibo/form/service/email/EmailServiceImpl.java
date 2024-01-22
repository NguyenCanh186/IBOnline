package com.vmg.ibo.form.service.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final ApplicationEventPublisher eventPublisher;

    public EmailServiceImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void sendEmail(String to, String subject, String text, List<String> cc) {

        logger.info("sendEmail");
        EmailTask emailTask = new EmailTask(to, subject, text, cc);
        eventPublisher.publishEvent(new EmailTaskEvent(this, emailTask));
    }

    @Data
    public class EmailTaskEvent extends ApplicationEvent {
        private final EmailTask emailTask;

        public EmailTaskEvent(Object source, EmailTask emailTask) {
            super(source);
            this.emailTask = emailTask;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class EmailTask {
        private String to;
        private String subject;
        private String text;
        private List<String> cc;
    }

    @Component
    public class EmailTaskProcessor {

        private final JavaMailSender javaMailSender;

        private final TaskExecutor taskExecutor;

        public EmailTaskProcessor(JavaMailSender javaMailSender, TaskExecutor taskExecutor) {
            this.javaMailSender = javaMailSender;
            this.taskExecutor = taskExecutor;
        }

        @EventListener
        public void handleEmailTaskEvent(EmailTaskEvent emailTaskEvent) {

            Object source = emailTaskEvent.getSource();

            if (source instanceof EmailServiceImpl) {
                EmailTask emailTask = emailTaskEvent.getEmailTask();
                taskExecutor.execute(() -> processEmailTask(emailTask));
            }
        }

        private void processEmailTask(EmailTask emailTask) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailTask.getTo());
            message.setSubject(emailTask.getSubject());
            message.setText(emailTask.getText());
            if (emailTask.getCc() != null && !emailTask.getCc().isEmpty()) {
                message.setCc(emailTask.getCc().toArray(new String[0]));
            }
            this.javaMailSender.send(message);
        }
    }
}
