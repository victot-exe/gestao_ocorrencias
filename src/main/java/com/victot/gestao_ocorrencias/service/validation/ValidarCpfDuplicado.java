package com.victot.gestao_ocorrencias.service.validation;

import com.victot.gestao_ocorrencias.dtos.request.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidarCpfDuplicado implements ValidadorNegocio<CriarPessoaRequest> {

    private final PessoaRepository pessoaRepository;

    @Override
    public void validate(CriarPessoaRequest target, Errors errors) {

        if(pessoaRepository.existsByCpf(target.getCpf())){
            errors.rejectValue("cpf", "cpf.duplicado", "Já existe uma pessoa com este CPF cadastrado.");
        }
    }
}
