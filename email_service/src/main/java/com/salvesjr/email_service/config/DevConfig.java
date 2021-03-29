package com.salvesjr.email_service.config;

import com.salvesjr.email_service.service.EmailService;
import com.salvesjr.email_service.service.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
