package com.salvesjr.library_events.consumer;

import com.salvesjr.library_events.service.LibraryEventsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LibraryEventsConsumer {

    public static final String CONSUMER_RECORD = "ConsumerRecord : {} ";
    private final LibraryEventsService libraryEventsService;

    public LibraryEventsConsumer(LibraryEventsService libraryEventsService) {
        this.libraryEventsService = libraryEventsService;
    }

    /**
     * Método responsável por escutar o tópico.
     *
     * @param consumerRecord
     * @throws IOException
     */
    @KafkaListener(topics = {"LIBRARY_EVENTS"})
    public void onMessageForTopicLibraryEvents(ConsumerRecord<Integer, GenericRecord> consumerRecord) throws IOException {

        log.info(CONSUMER_RECORD, consumerRecord);
        libraryEventsService.processTopicLibraryEvent(consumerRecord);
    }
}
