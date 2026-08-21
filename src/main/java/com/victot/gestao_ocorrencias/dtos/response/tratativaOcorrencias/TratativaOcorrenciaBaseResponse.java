package com.victot.gestao_ocorrencias.dtos.response.tratativaOcorrencias;

import com.victot.gestao_ocorrencias.enums.StatusOcorrencia;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class TratativaOcorrenciaBaseResponse {
    private String id;
    private String descricao;
    private LocalDateTime dataHoraTratativa;
    private String nomePessoaTratativa;
    private StatusOcorrencia statusOcorrencia;
}
