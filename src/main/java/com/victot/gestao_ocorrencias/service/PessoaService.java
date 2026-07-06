package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.pessoas.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.dtos.request.pessoas.EditarPessoaRequest;
import com.victot.gestao_ocorrencias.dtos.response.PessoaResponse;
import com.victot.gestao_ocorrencias.entity.Pessoa;
import com.victot.gestao_ocorrencias.exceptions.ResourceNotFoundException;
import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final List<ValidadorNegocio<? super CriarPessoaRequest>> criarPessoaValidators;
    private final List<ValidadorNegocio<EditarPessoaRequest>> editarPessoaValidators;

    //region Create
    public PessoaResponse create(CriarPessoaRequest request){
        validateCriarPessoa(request);

        var pessoa = new Pessoa(request.getNome(), request.getCpf(), request.getCargo());
        var pessoaSalva = pessoaRepository.save(pessoa);
        return converterParaResponse(pessoaSalva);
    }

    private void validateCriarPessoa(CriarPessoaRequest request){
        Errors erros = new BeanPropertyBindingResult(request, "criarPessoaRequest");

        for(var validator: criarPessoaValidators){
            validator.validate(request, erros);
        }

        if(erros.hasErrors()){
            throw new ValidacaoNegocioException(erros, "Falha ao preencher o formulário.");
        }
    }
    //endregion

    //region get
    //region byId
    public PessoaResponse getById(String id) {
        var pessoa = pessoaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Pessoa não encontrada."));

        return converterParaResponse(pessoa);
    }
    //endregion

    //region paginado
    public Page<PessoaResponse> getPaginado(Pageable paginationRequest) {
        var pageEntity = pessoaRepository.findAll(paginationRequest);

        if(pageEntity.isEmpty()){
            throw new ResourceNotFoundException("Pessoa não encontrada.");
        }

        return pageEntity.map(this::converterParaResponse);
    }
    //endregion

    //endregion

    //region edit
    public PessoaResponse edit(EditarPessoaRequest request) {
        validateEditarPessoa(request);

        var pessoa = pessoaRepository.findById(request.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Pessoa não encontrada."));

        pessoa.Atualizar(request.getNome(), request.getCpf(), request.getCargo());
        pessoaRepository.save(pessoa);
        return converterParaResponse(pessoa);
    }

    private void validateEditarPessoa(EditarPessoaRequest request){
        Errors erros = new BeanPropertyBindingResult(request, "editarPessoaRequest");

        for(var validator: criarPessoaValidators){
            validator.validate(request, erros);
        }

        for(var validator: editarPessoaValidators){
            validator.validate(request, erros);
        }

        if(erros.hasErrors()){
            throw new ValidacaoNegocioException(erros, "Falha ao preencher o formulário.");
        }
    }
    //endregion

    //region private
    private PessoaResponse converterParaResponse(@NotNull Pessoa entity){
        return new PessoaResponse(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getCargoFuncao()
        );
    }
    //endregion
}
