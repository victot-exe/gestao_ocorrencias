package com.victot.gestao_ocorrencias.dtos.response.ocorrencias;

import com.victot.gestao_ocorrencias.enums.StatusOcorrencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OcorrenciaDetalhadoResponse extends OcorrenciaResponseBase{
    private String Descricao;

    public OcorrenciaDetalhadoResponse(String id, String nomePessoa, String codigoModalidade, LocalDateTime dataHoraOcorrencia,  String Descricao, StatusOcorrencia statusOcorrencia) {
        super(id, nomePessoa, codigoModalidade, dataHoraOcorrencia, statusOcorrencia);
        this.Descricao = Descricao;
    }
}
