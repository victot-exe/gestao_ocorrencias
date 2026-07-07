package com.victot.gestao_ocorrencias.dtos.request.pessoas;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditarPessoaRequest extends CriarPessoaRequest {

    @NotBlank(message = "O ID é obrigatório para edição.")
    @UUID(message = "O ID fornecido não é um UUID válido.")
    private String id;
}
