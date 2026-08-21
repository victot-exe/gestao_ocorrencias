package com.victot.gestao_ocorrencias.service.validation.tratativaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.EditarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.repository.TratativaOcorrenciaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidateDataTratativaMaiorDataOcorrencia implements ValidadorNegocio<EditarTratativaOcorrenciaRequest> {

    private final TratativaOcorrenciaRepository tratativaOcorrenciaRepository;


    @Override
    public void validate(EditarTratativaOcorrenciaRequest target, Errors errors) {
        if (target.getDataHoraTratativa() == null || target.getIdTratativaOcorrencia() == null) {
            return;
        }
        tratativaOcorrenciaRepository.findById(target.getIdTratativaOcorrencia()).ifPresent(tratativa -> {
            var ocorrencia = tratativa.getOcorrencia();

            if(ocorrencia.getDataHoraOcorrencia().isAfter(target.getDataHoraTratativa())){
                errors.rejectValue("dataHoraTratativa", "data.invalida", "A data da tratativa deve ser posterior a data da ocorrência.");
            }
        });
    }
}
