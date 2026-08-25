package com.victot.gestao_ocorrencias.dtos.auth;

public record TokenResponse(String token, String type, String role, String nome) {
    public TokenResponse(String token, String role, String nome){
        this(token, "Bearer", role, nome);
    }
}
