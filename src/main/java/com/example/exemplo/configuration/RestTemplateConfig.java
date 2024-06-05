//package com.example.exemplo.configuration;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class RestTemplateConfig {
//
//    private final RestTemplateRequestInterceptor restTemplateRequestInterceptor;
//
//    public RestTemplateConfig(RestTemplateRequestInterceptor restTemplateRequestInterceptor) {
//        this.restTemplateRequestInterceptor = restTemplateRequestInterceptor;
//    }
//
//    // Aqui ele tá estabelecendo que os rest templates que seram instânciados já terá nele o body e a request.
//    // Com o @Bean todas as classes de rest templates criadas vão ter as instância como responsabilidade do spring(@Autowired)
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.interceptors(restTemplateRequestInterceptor).build();
//    }
//}
