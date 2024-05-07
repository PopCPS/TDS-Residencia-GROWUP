package com.example.exemplo.configuration.strateegia;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrateegiaRestTemplateConfiguration {

    public static final String STRATEEGIA_REST_TEMPLATE_BEAN = "strateegiaRestTemplateBean";

    @Bean(STRATEEGIA_REST_TEMPLATE_BEAN)
    public RestTemplateBuilder restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://api.strateegia.digital/users");
    }


}
