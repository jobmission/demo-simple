package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class WebConfig {

    /**
     * html视图处理, 注意不要与其他模板引擎冲突
     */
    @Bean
    @Order(1)
    public ViewResolver myCustomViewResolver(ResourceLoader resourceLoader) {
        return new SimpleViewResolver(resourceLoader);
    }
}
