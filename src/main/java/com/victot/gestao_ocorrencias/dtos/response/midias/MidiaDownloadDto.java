package com.victot.gestao_ocorrencias.dtos.response.midias;

import org.springframework.core.io.Resource;

public record MidiaDownloadDto(
        Resource resource,
        String contentType,
        String nomeOriginal
) {}
