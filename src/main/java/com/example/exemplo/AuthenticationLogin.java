package com.example.exemplo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class AuthenticationLogin {
  CustomBasicAuthenticationFilter customBasicAuthenticationFilter = new CustomBasicAuthenticationFilter();

  @GetMapping("/publica")
  public ResponseEntity<String> rotassPrivada() {
    return ResponseEntity.ok("user Logado");
  }


}
