package com.victot.gestao_ocorrencias.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MidiaOcorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "ocorrencia_id", nullable = false)
    private Ocorrencia ocorrencia;

    @Column(nullable = false, name = "nome_armazenado")
    private String nomeArmazenado;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @Column(nullable = false, name = "nome_original")
    private String nomeOriginal;

    @Column(nullable = false, length = 100, name = "content_type")
    private String contentType;

    @Column(nullable = false, name = "tamanho_bytes")
    private long tamanhoBytes;

    @Column(nullable = false, name = "data_hora_upload")
    private LocalDateTime dataHoraUpload;

    public MidiaOcorrencia(Ocorrencia ocorrencia, String nomeArmazenado, Pessoa pessoa, String nomeOriginal, String contentType, long tamanhoBytes, LocalDateTime dataHoraUpload) {
        this.ocorrencia = ocorrencia;
        this.nomeArmazenado = nomeArmazenado;
        this.pessoa = pessoa;
        this.nomeOriginal = nomeOriginal;
        this.contentType = contentType;
        this.tamanhoBytes = tamanhoBytes;
        this.dataHoraUpload = dataHoraUpload;
    }
}
