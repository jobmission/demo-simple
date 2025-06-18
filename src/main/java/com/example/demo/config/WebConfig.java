package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class WebConfig {

    @Bean
    public ViewResolver myCustomViewResolver(ResourceLoader resourceLoader) {
        return new SimpleViewResolver(resourceLoader);
    }
}
