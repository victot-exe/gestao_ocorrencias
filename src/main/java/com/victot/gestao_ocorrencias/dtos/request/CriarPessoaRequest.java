package com.victot.gestao_ocorrencias.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarPessoaRequest {
    private String nome;
    private String cpf;
    private String cargo;
}
