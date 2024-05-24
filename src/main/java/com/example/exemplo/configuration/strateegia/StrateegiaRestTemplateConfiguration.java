package com.example.exemplo.configuration.strateegia;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
public class StrateegiaRestTemplateConfiguration {

    public static final String STRATEEGIA_REST_TEMPLATE = "strateegiaRestTemplate";

    @Bean(STRATEEGIA_REST_TEMPLATE)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                     ObjectMapper objectMapper) {
        return restTemplateBuilder.messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                //.interceptors(new LoggingInterceptor())
                .rootUri("https://api.strateegia.digital")
                .build();
    }

}
