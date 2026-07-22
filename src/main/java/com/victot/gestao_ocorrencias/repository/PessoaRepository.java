package com.victot.gestao_ocorrencias.repository;

import com.victot.gestao_ocorrencias.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    boolean existsByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, String id);

    Optional<Pessoa> findByCpf(String cpf);//TODO Conferir o retorno
}
