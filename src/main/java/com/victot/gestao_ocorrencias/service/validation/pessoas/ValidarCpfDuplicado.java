package com.victot.gestao_ocorrencias.service.validation.pessoas;

import com.victot.gestao_ocorrencias.dtos.request.pessoas.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.dtos.request.pessoas.EditarPessoaRequest;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidarCpfDuplicado implements ValidadorNegocio<CriarPessoaRequest> {

    private final PessoaRepository pessoaRepository;

    @Override
    public void validate(CriarPessoaRequest target, Errors errors) {

        // Cenario 1: É uma Edição de Pessoa
        if (target instanceof EditarPessoaRequest editarRequest) {
            boolean cpfJaExisteEmOutraPessoa = pessoaRepository.existsByCpfAndIdNot(
                    editarRequest.getCpf(),
                    editarRequest.getId()
            );

            if (cpfJaExisteEmOutraPessoa) {
                errors.rejectValue("cpf", "duplicado", "Este CPF já está sendo usado por outra pessoa.");
            }

            // Cenario 2: É uma Criação Comum de Pessoa
        } else {
            if (pessoaRepository.existsByCpf(target.getCpf())) {
                errors.rejectValue("cpf", "duplicado", "Já existe uma pessoa cadastrada com este CPF.");
            }
        }
    }
}
