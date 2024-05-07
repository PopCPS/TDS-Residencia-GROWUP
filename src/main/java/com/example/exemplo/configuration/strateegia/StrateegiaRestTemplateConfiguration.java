package com.example.exemplo.configuration.strateegia;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class StrateegiaRestTemplateConfiguration {

    public static final String STRATEEGIA_REST_TEMPLATE_BEAN = "strateegiaRestTemplateBean";

    @Bean(STRATEEGIA_REST_TEMPLATE_BEAN)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://api.strateegia.digital/users").build();
    }


}
