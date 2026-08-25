package com.victot.gestao_ocorrencias.dtos.response.pessoas;

import com.victot.gestao_ocorrencias.enums.PerfilUsuario;
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
    private PerfilUsuario perfil;
}
