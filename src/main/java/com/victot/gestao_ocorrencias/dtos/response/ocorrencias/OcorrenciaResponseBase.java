package com.victot.gestao_ocorrencias.dtos.response.ocorrencias;

import com.victot.gestao_ocorrencias.enums.StatusOcorrencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OcorrenciaResponseBase {
    private String id;
    private String nomePessoa;
    private String codigoModalidade;
    private LocalDateTime dataHoraOcorrencia;
    private StatusOcorrencia statusAtual;
}
