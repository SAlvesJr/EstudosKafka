package com.salvesjr.email_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salvesjr.email_service.model.dto.EmailDto;
import com.sunilvb.demo.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

@Slf4j
public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String send;

//    private ModelMapper modelMapper;

    /**
     * Método que processar o a informação passado pelo tópico.
     *
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    public void processTopicSendEmail(ConsumerRecord<Integer, Email> consumerRecord) throws JsonProcessingException {
        ModelMapper modalMapper = new ModelMapper();
        EmailDto emailDto = modalMapper.map(consumerRecord.value(), EmailDto.class);
        log.info("Email log : {} ", emailDto);

        var sm = prepareSimpleMailMessageFromPedido(emailDto);
        sendEmail(sm);
    }


    /**
     * Método auxiliar para monta o email a ser enviado.
     *
     * @param obj
     * @return
     */
    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(EmailDto obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getClienteDto().getEmail());
        sm.setFrom(send);
        sm.setSubject("A Ordem esta sendo processada de Pedido");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.getBody() + "n/" + obj.getClienteDto().getNome() +  "n/" + obj.getBody());
        return sm;
    }
}
