package com.example.exemplo.configuration.security;

import lombok.Data;

@Data
public class StrateegiaSigninResponseDTO {
    String accessToken;
    String refreshToken;
}
