package com.victot.gestao_ocorrencias.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column()
//    @Column(nullable = false)
    private String cargoFuncao;//vai virar um tipo proprio tambem tablee funcoes

    public Pessoa(String nome, String cpf, String cargoFuncao) {
        this.nome = nome;
        this.cargoFuncao = cargoFuncao;
        this.cpf = cpf;
    }

    public void Atualizar(String nome, String cpf, String cargoFuncao) {
        this.nome = nome;
        this.cargoFuncao = cargoFuncao;
        this.cpf = cpf;
    }
}
