package edu.tsinghua.dmcs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by caizj on 18-9-22.
 */
@Component
public class SendEmail {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimEmail(String sendTo, String title, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("caizj15@163.com");
        message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        javaMailSender.send(message);
    }
}
