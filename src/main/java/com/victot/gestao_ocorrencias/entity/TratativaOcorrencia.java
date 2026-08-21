package com.victot.gestao_ocorrencias.entity;

import com.victot.gestao_ocorrencias.enums.StatusOcorrencia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tratativa_ocorrencia")
@Getter
@Setter
@NoArgsConstructor
public class TratativaOcorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "status_codigo", nullable = false, length = 3)
    private StatusOcorrencia status;

    @Column(name = "data_hora_tratativa", nullable = false)
    private LocalDateTime dataHoraTratativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ocorrencia_id", nullable = false)
    private Ocorrencia ocorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    public TratativaOcorrencia(Pessoa pessoa,  Ocorrencia ocorrencia, String descricao, StatusOcorrencia status, LocalDateTime dataHoraTratativa) {
        this.pessoa = pessoa;
        this.ocorrencia = ocorrencia;
        this.descricao = descricao;
        this.status = status;
        this.dataHoraTratativa = dataHoraTratativa;
    }
}
