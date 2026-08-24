package com.victot.gestao_ocorrencias.service.validation.midiaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.midiaOcorrencia.MidiaOcorrenciaDto;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("validarOcorrenciaExistenteParaMidia")
@RequiredArgsConstructor
public class ValidarOcorrenciaExistente implements ValidadorNegocio<MidiaOcorrenciaDto> {

    private final OcorrenciaRepository ocorrenciaRepository;
    @Override
    public void validate(MidiaOcorrenciaDto target, Errors errors) {
        var existe = ocorrenciaRepository.existsById(target.getIdOcorrencia());

        if(!existe)
            errors.rejectValue("idOcorrencia", "idOcorrencia.inexistente", "Ocorrência não encontrada.");
    }
}
