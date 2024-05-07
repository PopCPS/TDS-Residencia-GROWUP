package com.example.exemplo.controller;
import com.example.exemplo.configuration.security.CustomBasicAuthenticationFilter;
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
