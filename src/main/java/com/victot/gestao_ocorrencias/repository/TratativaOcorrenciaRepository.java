package com.victot.gestao_ocorrencias.repository;

import com.victot.gestao_ocorrencias.dtos.response.tratativaOcorrencias.TratativaOcorrenciaBaseResponse;
import com.victot.gestao_ocorrencias.entity.TratativaOcorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TratativaOcorrenciaRepository  extends
        JpaRepository<TratativaOcorrencia, String>,
        JpaSpecificationExecutor<TratativaOcorrencia> {

    @Query("""
            SELECT new com.victot.gestao_ocorrencias.dtos.response.tratativaOcorrencias.TratativaOcorrenciaBaseResponse(
                t.id,
                t.descricao,
                t.dataHoraTratativa,
                p.nome,
                t.status
            )
            FROM TratativaOcorrencia t
            LEFT JOIN t.pessoa p
            WHERE t.ocorrencia.id = :idOcorrencia
        """)
    Page<TratativaOcorrenciaBaseResponse> findByOcorrenciaIdPaginado(
            @Param("idOcorrencia") String idOcorrencia,
            Pageable pageable
    );
}
