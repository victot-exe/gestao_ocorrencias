package com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victot.gestao_ocorrencias.enums.StatusOcorrencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

@Setter
@Getter
public class CriarTratativaOcorrenciaRequest {
    @JsonIgnore
    private String pessoaAberturaId;

    @UUID
    @NotBlank
    private String ocorrenciaId;

    @NotNull(message = "a data da trativa é obrigatório.")
    private LocalDateTime dataHoraTratativa;

    @NotNull(message = "o status da ocorrencia é obrigatório.")
    private StatusOcorrencia statusOcorrencia;

    @NotBlank(message = "a descrição da tratativa é obrigatória.")
    @Size(max = 500, message = "o tamanho ultrapassa o definido pelo sistema.")
    private String descricao;

}
