package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.entity.Pessoa;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa create(CriarPessoaRequest request){

        //validacoes usando o negociuo do spring,
        //Nome nao pode ser vazio nem somente espacos, cpf tem que ter 11 digitos e seguir a validacao de cpf, cargo pode ser qualquer coisa, so precisa respeitar a quantidade de caracteres
        //o erro retornado deve ter nome da prop e uma mensagem personalizavel, acho que o spring já faz isso
        var pessoa = new Pessoa(request.getNome(), request.getCpf(), request.getCargo());

        return pessoaRepository.save(pessoa);
    }
}
