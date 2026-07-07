package com.victot.gestao_ocorrencias.service.validation.pessoas;

import com.victot.gestao_ocorrencias.dtos.request.pessoas.EditarPessoaRequest;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidarPessoaExistente implements ValidadorNegocio<EditarPessoaRequest> {
    private final PessoaRepository pessoaRepository;

    @Override
    public void validate(EditarPessoaRequest target, Errors errors) {
        var existe =  pessoaRepository.existsById(target.getId());

        if(existe)
            return;

        errors.rejectValue("id", "id.inexistente", "Não uma pessoa com este Id cadastrado.");
    }
}
