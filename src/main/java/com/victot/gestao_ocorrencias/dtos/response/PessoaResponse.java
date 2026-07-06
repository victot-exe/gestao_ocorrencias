package com.victot.gestao_ocorrencias.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PessoaResponse {
    private String id;
    private String nome;
    private String cpf;
    private String cargo;
}
