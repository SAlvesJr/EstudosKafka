package com.libraryevents.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@Slf4j
public class LibraryEventsConsumerConfig {

    //    LibraryEventsService libraryEventsService;
//    KafkaProperties kafkaProperties;
//
//    public LibraryEventsConsumerConfig(KafkaProperties kafkaProperties,
//                                       LibraryEventsService libraryEventsService) {
//        this.kafkaProperties = kafkaProperties;
//        this.libraryEventsService = libraryEventsService;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
//    ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
//            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
//            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
//
//        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

//        configurer.configure(factory, kafkaConsumerFactory
//                .getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(this.kafkaProperties.buildConsumerProperties())));
//        configurer.configure(factory, kafkaConsumerFactory);
//        factory.setConcurrency(2);
//        factory.setErrorHandler((thrownException, data) ->
//                log.info("Exception in consumerConfig is {} and the record is {}", thrownException.getMessage(), data)
//        );
//
//        factory.setRetryTemplate(retryTemplate());
//        factory.setRecoveryCallback((context -> {
//            if (context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
//                log.info("Inside the recoverable logic");
//                Arrays.asList(context.attributeNames())
//                        .forEach(attributeName -> {
//                            log.info("Attribute name is : {} ", attributeName);
//                            log.info("Attribute Value is : {} ", context.getAttribute(attributeName));
//                        });
//
//                ConsumerRecord<Integer, String> consumerRecord =
//                        (ConsumerRecord<Integer, String>) context.getAttribute("record");
//                libraryEventsService.handleRecovery(consumerRecord);
//            } else {
//                log.info("Inside the non recoverable logic");
//                throw new ConsumerException(context.getLastThrowable().getMessage());
//            }
//            return null;
//        }));
//        return factory;
//    }
//
//
//    private RetryTemplate retryTemplate() {
//        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
//        fixedBackOffPolicy.setBackOffPeriod(1000);
//        RetryTemplate retryTemplate = new RetryTemplate();
//        retryTemplate.setRetryPolicy(simpleRetryPolicy());
//        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
//        return retryTemplate;
//    }
//
//    private RetryPolicy simpleRetryPolicy() {
//        Map<Class<? extends Throwable>, Boolean> exceptionsMap = new HashMap<>();
//        exceptionsMap.put(IllegalArgumentException.class, false);
//        exceptionsMap.put(RecoverableDataAccessException.class, true);
//        return new SimpleRetryPolicy(3, exceptionsMap, true);
//    }
}
