package com.pohovor.demo.services;

import com.pohovor.demo.entity.email.EmailConfig;
import com.pohovor.demo.entity.email.EmailDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private EmailConfig emailConfig;

    public EmailService(JavaMailSender javaMailSender,
                        EmailConfig emailConfig) {
        this.javaMailSender = javaMailSender;
        this.emailConfig = emailConfig;
    }

    public void sendEmail(EmailDTO emailDTO) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailDTO.getEmailFrom());
        mailMessage.setTo(emailDTO.getEmailTo());
        mailMessage.setSubject(emailDTO.getSubject());
        mailMessage.setText(emailDTO.getMessage());

        mailSender.send(mailMessage);
    }
}
