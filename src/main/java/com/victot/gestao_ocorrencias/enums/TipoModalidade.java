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

    public static TipoModalidade doCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (TipoModalidade modalidade : values()) {
            if (modalidade.getCodigo().equals(codigo)) {
                return modalidade;
            }
        }
        throw new IllegalArgumentException("Código de modalidade inválido: " + codigo);
    }
}
