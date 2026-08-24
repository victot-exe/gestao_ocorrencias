package com.victot.gestao_ocorrencias.dtos.response.midias;

import com.victot.gestao_ocorrencias.entity.MidiaOcorrencia;

import java.time.LocalDateTime;

public record MidiaResponse(
        String id,
        String nomeOriginal,
        String contentType,
        long tamanhoBytes,
        LocalDateTime dataHoraUpload,
        String idPessoaUpload,
        String nomePessoaUpload
){
    public MidiaResponse(MidiaOcorrencia midia){
        this(
                midia.getId(),
                midia.getNomeOriginal(),
                midia.getContentType(),
                midia.getTamanhoBytes(),
                midia.getDataHoraUpload(),
                midia.getPessoa().getId(),
                midia.getPessoa().getNome()
        );
    }
}
