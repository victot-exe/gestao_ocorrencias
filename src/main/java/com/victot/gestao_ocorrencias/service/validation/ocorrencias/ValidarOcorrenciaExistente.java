package com.victot.gestao_ocorrencias.service.validation.ocorrencias;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.EditarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidarOcorrenciaExistente implements ValidadorNegocio<EditarOcorrenciaRequest> {

    private final OcorrenciaRepository ocorrenciaRepository;

    @Override
    public void validate(EditarOcorrenciaRequest target, Errors errors) {
        var existe = ocorrenciaRepository.existsById(target.getId());

        if(!existe)
            errors.rejectValue("id", "id.inexistente", "Ocorrência não encontrada.");
    }
}
