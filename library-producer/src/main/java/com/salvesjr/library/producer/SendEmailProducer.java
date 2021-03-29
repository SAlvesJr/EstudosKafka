package com.salvesjr.library.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salvesjr.library.exceptions.OnFailureException;
import com.salvesjr.library.model.dto.EmailDto;
import com.salvesjr.library.model.dto.LibraryEventDto;
import com.sunilvb.demo.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class SendEmailProducer {

    private final KafkaTemplate<Integer, Email> kafkaTemplate;
    private final ModelMapper modelMapper;

    public SendEmailProducer(KafkaTemplate<Integer, Email> kafkaTemplate, ModelMapper modelMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.modelMapper = modelMapper;
    }

    /**
     * Método para criar um emailDto a ser enviado no tópico.
     *
     * @param libraryEventsDto
     * @throws JsonProcessingException
     */
    public void createEmailToSend(LibraryEventDto libraryEventsDto) {
        var emailBody = "Thank you for your order! We are processing your order!";
        var email = new EmailDto(libraryEventsDto.getCliente(), emailBody, LocalDate.now());
        sendEmailMensagem(email);
    }

    /**
     * Método de envio de uma tópico.
     *
     * @param sendEmailDto
     * @throws JsonProcessingException
     */
    private void sendEmailMensagem(EmailDto sendEmailDto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = sendEmailDto.getDate().format(formatter);

        var key = 43847;//sendEmailDto.getClienteDto().getClienteId();
        var value = modelMapper.map(sendEmailDto, Email.class);
        value.setDate(formattedString);

        var producerRecord = buildProducerRecord(key, value);
        var listenableFuture = kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, Email> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    /**
     * Método auxiliar para gerar o producerRecord.
     *
     * @param key
     * @param value
     * @return
     */
    private ProducerRecord<Integer, Email> buildProducerRecord(Integer key, Email value) {
        return new ProducerRecord<>("LIBRARY_EVENTS_EMAIL", key, value);
    }

    /**
     * Método auxiliar para gerar um exception em caso de falha.
     *
     * @param ex
     */
    private void handleFailure(Throwable ex) {
        log.error("Error Send the Message and the exception");
        throw new OnFailureException(ex.getMessage());
    }

    /**
     * Método auxiliar para exibir o log de sucesso.
     *
     * @param key
     * @param value
     * @param result
     */
    private void handleSuccess(Integer key, Email value, SendResult<Integer, Email> result) {
        log.info("Message Sent SuccessFully for the key: {} and the value is {}, partition is {}",
                key, value, result.getProducerRecord().partition());
    }
}
