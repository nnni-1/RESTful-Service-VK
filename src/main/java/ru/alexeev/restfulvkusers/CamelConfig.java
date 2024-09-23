package ru.alexeev.restfulvkusers;

import org.apache.camel.ProducerTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {
    @Bean
    public ProducerTemplate producerTemplate(org.apache.camel.CamelContext camelContext) {
        return camelContext.createProducerTemplate();
    }
}
