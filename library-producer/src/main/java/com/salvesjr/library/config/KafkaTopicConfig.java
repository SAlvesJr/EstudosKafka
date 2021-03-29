package com.salvesjr.library.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    /**
     * Método de criação de uma típico.
     *
     * @return
     */
    @Bean
    public NewTopic topicLibraryEvent() {
        return TopicBuilder.name("LIBRARY_EVENTS").partitions(1).replicas(1).build();
    }

    /**
     * Método de criação de uma típico.
     *
     * @return
     */
    @Bean
    public NewTopic topicSendEmail() {
        return TopicBuilder.name("LIBRARY_EVENTS_EMAIL").partitions(1).replicas(1).build();
    }

}
