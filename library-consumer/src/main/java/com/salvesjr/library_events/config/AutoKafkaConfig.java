package com.salvesjr.library_events.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class AutoKafkaConfig {

    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}
}
