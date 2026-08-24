package com.victot.gestao_ocorrencias.repository;

import com.victot.gestao_ocorrencias.entity.MidiaOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MidiaOcorrenciaRepository extends JpaRepository<MidiaOcorrencia, String> {

    List<MidiaOcorrencia> findByOcorrenciaId(String ocorrenciaId);
    Optional<MidiaOcorrencia> findByIdAndOcorrenciaId(String id, String ocorrenciaId);
}
