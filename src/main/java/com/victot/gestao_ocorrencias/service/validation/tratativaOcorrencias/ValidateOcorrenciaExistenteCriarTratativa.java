package com.victot.gestao_ocorrencias.service.validation.tratativaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.CriarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidateOcorrenciaExistenteCriarTratativa implements ValidadorNegocio<CriarTratativaOcorrenciaRequest> {

    private final OcorrenciaRepository ocorrenciaRepository;

    @Override
    public void validate(CriarTratativaOcorrenciaRequest target, Errors errors) {
        var existe = ocorrenciaRepository.existsById(target.getOcorrenciaId());

        if(existe) return;

        errors.rejectValue("ocorrenciaId", "ocorrenciaId.inexistente", "Ocorrência não encontrada.");
    }
}
