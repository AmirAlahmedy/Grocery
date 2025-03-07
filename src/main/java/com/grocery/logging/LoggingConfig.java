package com.grocery.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public LoggerFactoryGrocery loggerFactoryGrocery() {
        return new LoggerFactoryGrocery();
    }
}
