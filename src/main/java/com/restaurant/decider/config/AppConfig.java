package com.restaurant.decider.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining beans in the application context.
 */
@Configuration
public class AppConfig {

    /**
     * Defines a bean for ModelMapper, which facilitates object mapping in the application.
     *
     * @return A ModelMapper bean
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
