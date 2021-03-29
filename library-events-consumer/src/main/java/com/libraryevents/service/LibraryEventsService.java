package com.libraryevents.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryevents.exception.OnFailureException;
import com.libraryevents.model.LibraryEvent;
import com.libraryevents.repository.LibraryEventsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LibraryEventsService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final LibraryEventsRepository libraryEventsRepository;

    public LibraryEventsService(KafkaTemplate<Integer, String> kafkaTemplate,
                                ObjectMapper objectMapper, LibraryEventsRepository libraryEventsRepository) {
        this.libraryEventsRepository = libraryEventsRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processLibraryEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        LibraryEvent libraryEvent = objectMapper.readValue(consumerRecord.value(), LibraryEvent.class);
        log.info("libraryEvent : {} ", libraryEvent);

        if (libraryEvent.getLibraryEventId() != null && libraryEvent.getLibraryEventId() == 0) {
            throw new RecoverableDataAccessException("Temporary Network Issue");
        }

        switch (libraryEvent.getLibraryEventType()) {
            case NEW:
                save(libraryEvent);
                break;
            case UPDATE:
                validate(libraryEvent);
                save(libraryEvent);
                break;
            default:
                log.info("Invalid Library Event Type");
        }

    }
//
//    public void handleRecovery(ConsumerRecord<Integer, String> record) {
//        Integer key = record.key();
//        String message = record.value();
//
//        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, message);
//        listenableFuture.addCallback(new ListenableFutureCallback<>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                handleFailure(ex);
//            }
//
//            @Override
//            public void onSuccess(SendResult<Integer, String> result) {
//                handleSuccess(key, message, result);
//            }
//        });
//    }


    private void validate(LibraryEvent libraryEvent) {
        if (libraryEvent.getLibraryEventId() == null) {
            throw new IllegalArgumentException("Library Event Id is missing");
        }

        Optional<LibraryEvent> libraryEventOptional = libraryEventsRepository.findById(libraryEvent.getLibraryEventId());
        log.info("Validation is successful for the library Event : {} ",
                libraryEventOptional.orElseThrow(() -> new IllegalArgumentException("Not a valid library Event")));
    }

    private void save(LibraryEvent libraryEvent) {
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventsRepository.save(libraryEvent);
        log.info("Successfully Persisted the library Event {} ", libraryEvent);
    }

    private void handleFailure(Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        throw new OnFailureException(ex.getMessage());
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                key, value, result.getRecordMetadata().partition());
    }
}
