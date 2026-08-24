package com.victot.gestao_ocorrencias.dtos.request.midiaOcorrencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class MidiaOcorrenciaDto {
    private MultipartFile arquivo;
    private String idPessoa;
    private String idOcorrencia;
}
