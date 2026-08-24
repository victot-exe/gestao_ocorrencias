package com.victot.gestao_ocorrencias.dtos.auth;

public record TokenResponse(String token, String type, String role) {
    public TokenResponse(String token, String role){
        this(token, "Bearer", role);
    }
}
