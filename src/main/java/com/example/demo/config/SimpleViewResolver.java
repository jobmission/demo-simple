package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Map;

public class SimpleViewResolver implements ViewResolver {
    private final ResourceLoader resourceLoader;

    public SimpleViewResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new View() {
            @Override
            public String getContentType() {
                return "text/html"; // 设置内容类型为HTML
            }

            @Override
            public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
                // 在这里处理你的逻辑，例如直接写入响应或通过其他方式生成HTML
                try {
                    Resource resource = resourceLoader.getResource("classpath:templates/" + viewName);
                    String htmlContent = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
                    String result = StringSubstitutor.replace(htmlContent, model);
                    response.getWriter().write(result);
                } catch (Exception e) {
                    throw new RuntimeException("Error reading HTML file: " + viewName, e);
                }
            }
        };
    }
}
