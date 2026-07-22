package com.victot.gestao_ocorrencias.dtos.auth;

public record TokenResponse(String token, String type) {
    public TokenResponse(String token){
        this(token, "Bearer");
    }
}
