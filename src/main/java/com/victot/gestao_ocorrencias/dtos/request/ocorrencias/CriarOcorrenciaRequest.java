package com.victot.gestao_ocorrencias.dtos.request.ocorrencias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victot.gestao_ocorrencias.enums.TipoModalidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

@Getter
@Setter
public class CriarOcorrenciaRequest {

    @JsonIgnore
    private String pessoaAberturaId;

    @NotNull
    private TipoModalidade  tipoModalidade;

    @NotBlank(message = "Descrição é obrigatório.")
    @Size(min = 2, max = 500)
    private String descricao;

    @NotNull(message = "A data é obrigatória.")
    private LocalDateTime dataHoraOcorrencia;
}
