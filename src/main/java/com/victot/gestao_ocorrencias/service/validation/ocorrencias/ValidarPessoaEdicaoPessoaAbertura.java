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
        ocorrenciaRepository.findById(target.getId()).ifPresent(ocorrencia -> {
            var pessoaAbertura = ocorrencia.getPessoaAbertura();

            if (pessoaAbertura == null || !pessoaAbertura.getId().equals(target.getPessoaAberturaId())) {
                errors.rejectValue("pessoaAberturaId", "unauthorized", "Você não tem permissão para editar esta ocorrência.");
            }
        });
    }
}
