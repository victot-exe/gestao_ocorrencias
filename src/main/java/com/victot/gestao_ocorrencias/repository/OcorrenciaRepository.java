package com.victot.gestao_ocorrencias.repository;

import com.victot.gestao_ocorrencias.entity.Ocorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface OcorrenciaRepository extends
        JpaRepository<Ocorrencia, String>,
        JpaSpecificationExecutor<Ocorrencia> {
    Page<Ocorrencia>findAllByPessoaAberturaId(Pageable pageable, String idPessoaAbertura);

    @Query("""
            SELECT DISTINCT o
            FROM Ocorrencia o
            LEFT JOIN FETCH o.pessoaAbertura
            LEFT JOIN FETCH o.tratativas t
            LEFT JOIN FETCH t.pessoa
            ORDER BY o.dataHoraOcorrencia DESC
            """)
    List<Ocorrencia> findAllComTratativas();
}
