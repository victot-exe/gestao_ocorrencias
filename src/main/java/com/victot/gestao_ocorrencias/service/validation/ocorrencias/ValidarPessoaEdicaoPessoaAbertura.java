package com.victot.gestao_ocorrencias.service.validation.ocorrencias;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.EditarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidarPessoaEdicaoPessoaAbertura implements ValidadorNegocio<EditarOcorrenciaRequest> {

    private final OcorrenciaRepository ocorrenciaRepository;
    @Override
    public void validate(EditarOcorrenciaRequest target, Errors errors) {
        var ocorrencia = ocorrenciaRepository.findById(target.getId())
                .orElse(null);//TODO ver como melhorar isso aqui ja que essa parte que existe eu checo em outra parte não coloquei o lançamento do erro aqui. só retornei e deixei o fluxo seguir
        if(ocorrencia == null) return;

        if(target.getPessoaAberturaId().equals(ocorrencia.getPessoaAbertura().getId())) return;

        errors.rejectValue("idPessoaAbertura", "unautorized", "Você não tem permissão para editar esta ocorrência.");
    }
}
