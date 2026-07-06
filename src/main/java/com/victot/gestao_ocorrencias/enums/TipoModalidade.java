package com.victot.gestao_ocorrencias.enums;

import lombok.Getter;

@Getter
public enum TipoModalidade {
    ROU("ROU", "Roubo"),
    ACI("ACI", "Acidente"),
    MAN("MAN", "Manutenção"),
    OUT("OUT", "Outro");

    private final String codigo;
    private final String nome;

    TipoModalidade(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }
}
