package com.victot.gestao_ocorrencias.dtos.request.ocorrencias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class FiltroOcorrenciaPorPessoa extends FiltroOcorrenciaBase {
    private String pessoaAberturaId;

    public FiltroOcorrenciaPorPessoa(FiltroOcorrenciaBase base, String pessoaAberturaId) {
        BeanUtils.copyProperties(base, this);
        this.pessoaAberturaId = pessoaAberturaId;
    }
}
