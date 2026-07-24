package com.victot.gestao_ocorrencias.enums;

import lombok.Getter;

@Getter
public enum PerfilUsuario {
    ADMIN("ROLE_ADMIN"),
    GESTOR("ROLE_GESTOR"),
    OPERADOR("ROLE_OPERADOR");

    private final String role;

    PerfilUsuario(String role) {
        this.role = role;
    }
}
