package com.victot.gestao_ocorrencias.enums;

import lombok.Getter;

@Getter
public enum StatusOcorrencia {
    CRI("CRI", "Criada"),
    EMR("EMR", "Em resolução"),
    RES("RES", "Resolvida"),
    NRE("NRE", "Não Resolvida"),;

    private final String codigo;
    private final String descricao;

    StatusOcorrencia(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static StatusOcorrencia doCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (StatusOcorrencia status : values()) {
            if (status.getCodigo().equals(codigo)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }
}
