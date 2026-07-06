package com.victot.gestao_ocorrencias.dtos.request;

import com.victot.gestao_ocorrencias.enums.TipoModalidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarOcorrenciaRequest {
    private String pessoaAberturaId;//futuramente vai ser pego pelo token
    private TipoModalidade  tipoModalidade;
    private String descricao;
    private LocalDateTime dataHoraOcorrencia;
}
