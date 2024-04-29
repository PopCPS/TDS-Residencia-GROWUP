package com.example.exemplo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class AuthenticationLogin {

  @PostMapping("/retornarUsuario")
  public String login(@RequestBody @Valid LoginDTO dado) {
    if (dado.login().equals("alice") && dado.senha().equals("1234")) {
      return "Usuário logado";
    }
    return "não logado";

  }

}
