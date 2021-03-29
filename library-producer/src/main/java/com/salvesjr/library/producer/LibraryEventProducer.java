package com.salvesjr.library.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salvesjr.library.exceptions.OnFailureException;
import com.salvesjr.library.model.dto.LibraryEventDto;
import com.sunilvb.demo.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class LibraryEventProducer {

    private final KafkaTemplate<Integer, Order> orderKafkaTemplate;
    private final ModelMapper modelMapper;

    @Value("${kafka.topic-library}")
    private String topic;

    public LibraryEventProducer(KafkaTemplate<Integer, Order> orderKafkaTemplate, ModelMapper modelMapper) {
        this.orderKafkaTemplate = orderKafkaTemplate;
        this.modelMapper = modelMapper;
    }

    /**
     * Método de envio de uma tópico.
     *
     * @param libraryEventsDto
     * @throws JsonProcessingException
     */
    public void sendTopicLibraryEvent(LibraryEventDto libraryEventsDto) {

        var key = 48628;
        var obj = modelMapper.map(libraryEventsDto, Order.class);

        var producerRecord = buildProducerRecord(key, obj);
        var listenableFuture = orderKafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, Order> result) {
                handleSuccess(key, obj, result);
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
    private ProducerRecord<Integer, Order> buildProducerRecord(Integer key, Order value) {
        return new ProducerRecord<>("LIBRARY_EVENTS", key, value);
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
    private void handleSuccess(Integer key, Order value, SendResult<Integer, Order> result) {
        log.info("Message Sent SuccessFully for the key: "
                        + "{} and the value is {}, partition is {} and delivered with offset {}",
                key, value, result.getProducerRecord().partition(), result.getRecordMetadata().offset());
    }
}
