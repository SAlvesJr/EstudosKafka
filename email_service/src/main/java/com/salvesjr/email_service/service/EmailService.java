package com.salvesjr.email_service.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage msg);
}
