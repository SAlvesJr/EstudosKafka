package com.salvesjr.email_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    /**
     * Método de envio de email.
     *
     * @param msg
     */
    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Envido de email");
        mailSender.send(msg);
        log.info("Email enviado com sucesso!");
    }
}
