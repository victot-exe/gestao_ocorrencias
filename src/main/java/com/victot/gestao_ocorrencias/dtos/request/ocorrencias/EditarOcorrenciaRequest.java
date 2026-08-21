package com.victot.gestao_ocorrencias.dtos.request.ocorrencias;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class EditarOcorrenciaRequest extends CriarOcorrenciaRequest {
    @NotBlank
    @UUID
    private String id;
}
