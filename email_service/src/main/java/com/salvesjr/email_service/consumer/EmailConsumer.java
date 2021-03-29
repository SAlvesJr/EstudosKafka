package com.salvesjr.email_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salvesjr.email_service.service.SmtpEmailService;
import com.sunilvb.demo.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    private final SmtpEmailService emailService;

    public EmailConsumer(SmtpEmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Método responsável por escutar o tópico.
     *
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = {"LIBRARY_EVENTS_SEND_EMAIL"}, groupId = "library-events-email", containerFactory = "kafkaListenerContainerFactory")
//    @KafkaListener(topics = "LIBRARY_EVENTS_EMAIL", groupId = "library-events-email", containerFactory = "kafkaListenerContainerFactory")
    public void onMessageTopicSendEmail(ConsumerRecord<Integer, Email> consumerRecord) throws JsonProcessingException {

        log.info("ConsumerRecord : {} ", consumerRecord);

        emailService.processTopicSendEmail(consumerRecord);
    }
}
