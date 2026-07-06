package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.entity.Ocorrencia;
import com.victot.gestao_ocorrencias.enums.TipoModalidade;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import org.springframework.stereotype.Service;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final PessoaRepository pessoaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, PessoaRepository pessoaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.pessoaRepository = pessoaRepository;
    }
    //TODO Criar Exceção que extende runtime e é uma exceção para lançar os erros dde regra de negocio, not found, ddai retorna automaticamente 404, se for outra coisa vai tratar tambem
    public Ocorrencia criarOcorrencia(CriarOcorrenciaRequest request){
//        var pessoa = pessoaRepository.getReferenceById(request.getPessoaAberturaId());//qual a diferenca dos dois??
        var pessoa = pessoaRepository.findById(request.getPessoaAberturaId())
                .orElseThrow(()-> new RuntimeException("Id pessoa não encontrado"));//fazer o validator que vai validar isso e retornar o erro antes de chegar aqui
        //ter qie fazer get pessoa para cada vez que criar a ocorrencia? não tem como adicinar somente a referencia pelo id? parecido com o ddotnet, otimizaria as idas ao bbanco de dados

        if(request.getTipoModalidade() == TipoModalidade.OUT || request.getDescricao().isBlank()){//qual a diferenca do isBlank pro isEmpty??
            throw new RuntimeException("A descrição é obrigatória para o tipo OUTRO.");//vai fazer nas validacoes tambem, usar aquele que faz a lista de validdacoes, fazer o jeito que retorna a prop que ta erradda e a mensagem do erro
        }

        var novaOcorrencia = new Ocorrencia(pessoa, request.getTipoModalidade(), request.getDescricao(), request.getDataHoraOcorrencia());
        return ocorrenciaRepository.save(novaOcorrencia);
    }
}
