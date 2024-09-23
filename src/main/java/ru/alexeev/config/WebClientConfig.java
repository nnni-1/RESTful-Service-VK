package ru.alexeev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <br>Конфигурационный класс для настройки {@link WebClient}
 * <br>Предоставляет бин {@link WebClient} с предустановленным базовым URL для взаимодействия с VK API
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient vkWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.vk.com/method")
                .build();
    }
}