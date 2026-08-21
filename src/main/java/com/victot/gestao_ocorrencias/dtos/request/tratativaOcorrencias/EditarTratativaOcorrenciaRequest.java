package com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;


@Getter
@Setter
public class EditarTratativaOcorrenciaRequest {

    @NotBlank
    @UUID
    private String idTratativaOcorrencia;

    @NotBlank
    @Size(max = 500)
    private String descricao;

    @NotNull
    private LocalDateTime dataHoraTratativa;

}
