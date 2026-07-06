package com.victot.gestao_ocorrencias.repository;

import com.victot.gestao_ocorrencias.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, String> {

}
