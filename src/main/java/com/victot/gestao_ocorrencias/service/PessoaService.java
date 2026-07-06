package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.entity.Pessoa;
import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import java.util.List;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final List<ValidadorNegocio<CriarPessoaRequest>> validarCriarPessoa;

    public PessoaService(PessoaRepository pessoaRepository, List<ValidadorNegocio<CriarPessoaRequest>> validarCriarPessoa) {
        this.pessoaRepository = pessoaRepository;
        this.validarCriarPessoa = validarCriarPessoa;
    }

    public Pessoa create(CriarPessoaRequest request){

        Errors erros = new BeanPropertyBindingResult(request, "criarPessoaRequest");

        for(var validator: validarCriarPessoa){
            validator.validate(request, erros);
        }

        if(erros.hasErrors()){
            throw new ValidacaoNegocioException(erros, "Falha ao preencher o formulário.");
        }
        
        var pessoa = new Pessoa(request.getNome(), request.getCpf(), request.getCargo());

        return pessoaRepository.save(pessoa);
    }
}
