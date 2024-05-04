package com.example.exemplo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthenticationLogin {
  @GetMapping("/privada")
  public String rotaPrivada() {

    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.strateegia.digital/users/v1/auth/signin";
    String[] requestBody = {"alicecavalcanti24@gmail.com", "senha"};
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String[]> requestEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<ResultDTO> responseEntity = restTemplate.postForEntity(url, requestEntity, ResultDTO.class);
    String resultado =  responseEntity.getBody().accessToken;
    return "certo";
  }
  @GetMapping("/publica")
  public ResponseEntity<String> rotassPrivada() {
    return ResponseEntity.ok("user Logado");
  }


}
