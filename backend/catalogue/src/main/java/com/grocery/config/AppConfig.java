package com.grocery.config;

import com.grocery.security.JwtUtil;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;

@Configuration
public class AppConfig {

    @Value("${spring.ai.ollama.base-url}")
    private String baseUrl;

    @Value("${spring.ai.ollama.chat.model}")
    private String model;

    @Value("${spring.ai.ollama.chat.temperature}")
    private double temperature;

    @Bean
    public OllamaChatModel ollamaChatModel() {
        OllamaApi ollamaApi = new OllamaApi(baseUrl);
        OllamaOptions ollamaOptions = OllamaOptions.builder()
                .model(OllamaModel.valueOf(model))
                .temperature(temperature)
                .build();

        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(ollamaOptions)
                .build();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}