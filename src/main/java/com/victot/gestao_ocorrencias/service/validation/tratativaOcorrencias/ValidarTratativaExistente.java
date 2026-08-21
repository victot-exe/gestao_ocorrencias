package com.victot.gestao_ocorrencias.service.validation.tratativaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.EditarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.repository.TratativaOcorrenciaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidarTratativaExistente implements ValidadorNegocio<EditarTratativaOcorrenciaRequest> {

    private  final TratativaOcorrenciaRepository tratativaOcorrenciaRepository;

    @Override
    public void validate(EditarTratativaOcorrenciaRequest target, Errors errors) {
        var existe = tratativaOcorrenciaRepository.existsById(target.getIdTratativaOcorrencia());

        if(!existe){
            errors.rejectValue("idTratativaOcorrencia", "id.inexistente", "Tratativa ocorrência não encontrada.");
        }
    }
}
