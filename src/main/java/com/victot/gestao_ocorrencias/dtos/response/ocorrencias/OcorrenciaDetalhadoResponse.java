package com.victot.gestao_ocorrencias.dtos.response.ocorrencias;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OcorrenciaDetalhadoResponse extends OcorrenciaResponseBase{
    private String Descricao;

    public OcorrenciaDetalhadoResponse(String id, String nomePessoa, String codigoModalidade, LocalDateTime dataHoraOcorrencia,  String Descricao) {
        super(id, nomePessoa, codigoModalidade, dataHoraOcorrencia);
        this.Descricao = Descricao;
    }
}
