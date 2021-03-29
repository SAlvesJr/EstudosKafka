package com.libraryevents.consumer;

import com.libraryevents.service.LibraryEventsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LibraryEventsConsumer {
    private final LibraryEventsService libraryEventsService;

    public LibraryEventsConsumer(LibraryEventsService libraryEventsService){
        this.libraryEventsService = libraryEventsService;
    }

    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws IOException {

        log.info("ConsumerRecord : {} ", consumerRecord );
        libraryEventsService.processLibraryEvent(consumerRecord);
    }
}