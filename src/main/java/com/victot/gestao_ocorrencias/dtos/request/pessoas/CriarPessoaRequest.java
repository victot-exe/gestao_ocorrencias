package com.victot.gestao_ocorrencias.dtos.request.pessoas;

import com.victot.gestao_ocorrencias.enums.PerfilUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarPessoaRequest {
    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 2, max = 100)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "O CPF fornecido é inválido.")
    private String cpf;
    private PerfilUsuario perfilUsuario;
    @NotBlank(message = "A senha é obrigatória.")
    private String password;
    private String confirmPassword;
}
