//package com.example.exemplo.configuration;
//
//import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Component
//public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {
//    private final StrateegiaInMemoryTokenStore tokenStore;
//
//    public RestTemplateRequestInterceptor() {
//        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
//    }
//
//    // O intercept é uma config que será adicionada ao rest template já para eu poder chamar o rest template com a request e o body.
//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//        String token = tokenStore.getTokenStore().get(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        request.getHeaders().set("Authorization", "Bearer " + token);
//        return execution.execute(request, body);
//    }
//}
