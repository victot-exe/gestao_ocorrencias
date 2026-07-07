package com.victot.gestao_ocorrencias.repository;

import com.victot.gestao_ocorrencias.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    boolean existsByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, String id);
}
