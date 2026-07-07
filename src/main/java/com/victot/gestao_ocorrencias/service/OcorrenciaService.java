package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaDetalhadoResponse;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaResponseBase;
import com.victot.gestao_ocorrencias.entity.Ocorrencia;
import com.victot.gestao_ocorrencias.exceptions.ResourceNotFoundException;
import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final PessoaRepository pessoaRepository;
    private final List<ValidadorNegocio<? super CriarOcorrenciaRequest>> validatorCriarOcorrencia;

    //region create
    public OcorrenciaResponseBase criarOcorrencia(CriarOcorrenciaRequest request){

        validateCriarOcorrencia(request);

        var pessoa = pessoaRepository.getReferenceById(request.getPessoaAberturaId());

        var novaOcorrencia = new Ocorrencia(pessoa, request.getTipoModalidade(), request.getDescricao(), request.getDataHoraOcorrencia());
        var entity = ocorrenciaRepository.save(novaOcorrencia);
        return converterParaResponseBase(entity);
    }

    private void validateCriarOcorrencia(CriarOcorrenciaRequest request){
        var errors = new BeanPropertyBindingResult(request, "criarOcorrenciaRequest");

        for(var validator: validatorCriarOcorrencia){
            validator.validate(request, errors);
        }

        if(errors.hasErrors()){
            throw new ValidacaoNegocioException(errors, "Falha ao preencher o formulário.");
        }
    }
    //endregion

    //region get

    //region byId
    public OcorrenciaDetalhadoResponse getById(String id) {
        var ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ocorrencia não encontrada."));
        return converterParaResponseDetalhado(ocorrencia);
    }
    //endregion

    //region paginado
    public Page<OcorrenciaResponseBase> getPageable(Pageable pageable){
        var data =  ocorrenciaRepository.findAll(pageable);
        return data.map(this::converterParaResponseBase);
    }
    //endregion

    //region pageableByIdPessoa
    public Page<OcorrenciaResponseBase> getPageableByIdPessoa(String idPessoaAbertura, Pageable pageable){

        var data =  ocorrenciaRepository.findAllByPessoaAberturaId(pageable, idPessoaAbertura);
        return data.map(this::converterParaResponseBase);
    }
    //endregion

    //endregion

    //region private methods
    private OcorrenciaResponseBase converterParaResponseBase(Ocorrencia ocorrencia){
        return new OcorrenciaResponseBase(ocorrencia.getId(), ocorrencia.getPessoaAbertura().getNome(), ocorrencia.getModalidade().getCodigo(), ocorrencia.getDataHoraOcorrencia());
    }

    private OcorrenciaDetalhadoResponse converterParaResponseDetalhado(Ocorrencia ocorrencia){
        return new OcorrenciaDetalhadoResponse(ocorrencia.getId(), ocorrencia.getPessoaAbertura().getNome(), ocorrencia.getModalidade().getCodigo(), ocorrencia.getDataHoraOcorrencia(), ocorrencia.getDescricao());
    }
    //endregion
}
