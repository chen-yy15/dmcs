package edu.tsinghua.dmcs.entity;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by caizj on 18-9-22.
 */
public class EmailConfig {
    @Value("${spring.mail.username}")
    private String emailForm;

    public String getEmailForm() {
        return emailForm;
    }

    public void setEmailForm(String emailForm) {
        this.emailForm = emailForm;
    }
}
