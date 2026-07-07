package com.victot.gestao_ocorrencias.entity;

import com.victot.gestao_ocorrencias.enums.TipoModalidade;
import com.victot.gestao_ocorrencias.enums.TipoModalidadeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoaAbertura;

    @Column(name = "modalidade_codigo", nullable = false, length = 3)
    @Convert(converter = TipoModalidadeConverter.class)
    private TipoModalidade modalidade;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataHoraOcorrencia;

    public Ocorrencia(Pessoa pessoaAbertura, TipoModalidade modalidade, String descricao, LocalDateTime dataHoraOcorrencia) {
        this.pessoaAbertura = pessoaAbertura;
        this.modalidade = modalidade;
        this.descricao = descricao;
        this.dataHoraOcorrencia = dataHoraOcorrencia;
    }
}
