package com.victot.gestao_ocorrencias.service.validation.midiaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.midiaOcorrencia.MidiaOcorrenciaDto;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ValidarTamanhoArquivo implements ValidadorNegocio<MidiaOcorrenciaDto> {
    @Override
    public void validate(MidiaOcorrenciaDto target, Errors errors) {
        var tamanhoArquivo = target.getArquivo().getSize();

        if(tamanhoArquivo <= 0L) {
            errors.rejectValue("arquivo", "arquivo.size", "arquivo invalido");
        }

        if(tamanhoArquivo > 52_428_800L) {
            errors.rejectValue("arquivo", "arquivo.size", "arquivo maior que o esperado.");
        }
    }
}
