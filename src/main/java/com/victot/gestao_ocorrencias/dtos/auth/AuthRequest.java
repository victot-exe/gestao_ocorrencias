package com.victot.gestao_ocorrencias.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest (
    @NotBlank(message = "O CPF é obrigatório") String cpf,
    @NotBlank(message = "A senha é obrigatória") String password
){}
