package com.libraryevents.consumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LibraryEventsConsumerManualOffset {
        //implements AcknowledgingMessageListener<Integer, String> {

//    @Override
//    @KafkaListener(topics = {"library-events"})
//    public void onMessage(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment acknowledgment) {
//        log.info("ConsumerRecord : {} ", consumerRecord);
//        acknowledgment.acknowledge();
//    }
}