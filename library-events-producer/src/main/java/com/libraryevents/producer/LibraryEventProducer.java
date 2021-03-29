package com.libraryevents.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryevents.domain.LibraryEvent;
import com.libraryevents.domain.LibraryEventType;
import com.libraryevents.domain.dto.LibraryEventDto;
import com.libraryevents.exceptions.OnFailureException;
import com.libraryevents.exceptions.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LibraryEventProducer {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    String topic = "library-events";

    public LibraryEventProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        var key = libraryEvent.getLibraryEventId();
        var value = objectMapper.writeValueAsString(libraryEvent);
        var listenableFuture = kafkaTemplate.sendDefault(key, value);

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    public ListenableFuture<SendResult<Integer, String>> sendLibraryEventApproachTwo(LibraryEvent libraryEvent) throws JsonProcessingException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, topic);
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
        return listenableFuture;
    }

    public void sendLibraryEventSynchronous(LibraryEvent libraryEvent) throws JsonProcessingException {

        var key = libraryEvent.getLibraryEventId();
        var value = objectMapper.writeValueAsString(libraryEvent);

        try {
            kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            log.error("ExecutionException Sending the Message and the exception is {}", e.getMessage());
            throw new OnFailureException(e.getMessage());
        } catch (Exception e) {
            log.error("Exception Sending the Message and the exception is {}", e.getMessage());
            throw new OnFailureException(e.getMessage());
        }
    }

    public LibraryEvent fromEntity(LibraryEventDto libraryEventDto) throws ValidateException {
        if (!libraryEventDto.getLibraryEventType().equalsIgnoreCase("new")) {
            throw new ValidateException("Erro nos campos passados!");
        }
        return new LibraryEvent(libraryEventDto.getLibraryEventId(), LibraryEventType.NEW, libraryEventDto.getBook());
    }

    public LibraryEvent validEntity(LibraryEventDto libraryEventDto, Integer idLibraryEvent) {
        if (!libraryEventDto.getLibraryEventType().equalsIgnoreCase("update")) {
            throw new ValidateException("Erro nos campos passados!");
        }
        return new LibraryEvent(idLibraryEvent, LibraryEventType.UPDATE, libraryEventDto.getBook());
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));

        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }

    private void handleFailure(Throwable ex) {
        log.error("Error Send the Message and the exception");
        throw new OnFailureException(ex.getMessage());

    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message Sent SuccessFully for the key: {} and the value is {}, partition is {}",
                key, value, result.getProducerRecord().partition());
    }
}
