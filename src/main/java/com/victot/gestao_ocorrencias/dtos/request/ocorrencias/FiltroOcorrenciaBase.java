package com.victot.gestao_ocorrencias.dtos.request.ocorrencias;

import com.victot.gestao_ocorrencias.enums.StatusOcorrencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FiltroOcorrenciaBase {
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private StatusOcorrencia statusAtual;
}
