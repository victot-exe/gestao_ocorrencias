package com.victot.gestao_ocorrencias.service.validation.midiaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.midiaOcorrencia.MidiaOcorrenciaDto;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.List;

@Component
public class ValidarFormatoArquivo implements ValidadorNegocio<MidiaOcorrenciaDto> {

    private static final List<String> FORMATOS_SUPORTADOS = List.of(
            // Imagens
            "image/png",
            "image/jpeg",
            "image/jpg",
            "image/webp",

            // Documentos
            "application/pdf",

            // Vídeos
            "video/mp4",
            "video/webm",
            "video/quicktime"
    );

    @Override
    public void validate(MidiaOcorrenciaDto target, Errors errors) {
        var formatoArquivo = target.getArquivo().getContentType();

        if(!FORMATOS_SUPORTADOS.contains(formatoArquivo)){
            errors.rejectValue("arquivo", "arquivo.format", "arquivo de formato inválido.");
        }
    }
}
