package com.vmg.ibo.form.service.email;

import java.util.List;

public interface EmailService {

    public void sendEmail(String to, String subject, String text, List<String> cc);

}
