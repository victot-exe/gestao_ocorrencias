package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.EditarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.FiltroOcorrenciaBase;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.FiltroOcorrenciaPorPessoa;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaDetalhadoResponse;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaResponseBase;
import com.victot.gestao_ocorrencias.entity.Ocorrencia;
import com.victot.gestao_ocorrencias.exceptions.ResourceNotFoundLocalException;
import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final PessoaRepository pessoaRepository;
    private final List<ValidadorNegocio<? super CriarOcorrenciaRequest>> validatorCriarOcorrencia;
    private final List<ValidadorNegocio<EditarOcorrenciaRequest>> validatorEditarOcorrencia;

    //region create
    public OcorrenciaResponseBase create(CriarOcorrenciaRequest request){

        validateCriarOcorrencia(request);

        var pessoa = pessoaRepository.getReferenceById(request.getPessoaAberturaId());

        var novaOcorrencia = new Ocorrencia(pessoa, request.getTipoModalidade(), request.getDescricao(), request.getDataHoraOcorrencia());
        var entity = ocorrenciaRepository.save(novaOcorrencia);
        return convertToOcorrenciaBaseResponse(entity);
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
                .orElseThrow(()-> new ResourceNotFoundLocalException("Ocorrencia não encontrada."));

        return convertToResponseDetalhado(ocorrencia);
    }
    //endregion

    //region paginado
    public Page<OcorrenciaResponseBase> getPageable(Pageable pageable){
        var data =  ocorrenciaRepository.findAll(pageable);
        return data.map(this::convertToOcorrenciaBaseResponse);
    }
    //endregion

    //region getPageableWithFilter
    public Page<OcorrenciaResponseBase> getPageableWithFilter(String idPessoa, FiltroOcorrenciaBase request, Pageable pageable){
        var filter = new FiltroOcorrenciaPorPessoa(request, idPessoa);
        return getPageableWithFilter(filter, pageable);
    }
    public Page<OcorrenciaResponseBase> getPageableWithFilter(FiltroOcorrenciaPorPessoa filter, Pageable pageable){
        var spec = specificationOcorrencias(filter);
        return ocorrenciaRepository.findAll(spec, pageable)
                .map(this::convertToOcorrenciaBaseResponse);
    }

    //region specificationOcorrencias
    private Specification<Ocorrencia> specificationOcorrencias(FiltroOcorrenciaPorPessoa filtro){

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filtro.getDataInicial() != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataHoraOcorrencia"), filtro.getDataInicial()));
            }
            if(filtro.getDataFinal() != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("dataHoraOcorrencia"), filtro.getDataFinal()));
            }

            if(filtro.getPessoaAberturaId() != null){
                predicates.add(cb.equal(root.get("pessoaAbertura").get("id"), filtro.getPessoaAberturaId()));
            }

            if(filtro.getStatusAtual() != null){
                predicates.add(cb.equal(root.get("statusAtual"), filtro.getStatusAtual()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    //endregion

    //endregion

    //endregion

    //region put
    public OcorrenciaResponseBase edit(EditarOcorrenciaRequest request) {
        validateEditarOcorrencia(request);

        var ocorrencia = ocorrenciaRepository.findById(request.getId())
                .orElseThrow(()-> new ResourceNotFoundLocalException("Ocorrencia não encontrada."));

        ocorrencia.editar(request.getTipoModalidade(), request.getDescricao(), request.getDataHoraOcorrencia());

        ocorrenciaRepository.save(ocorrencia);
        return convertToOcorrenciaBaseResponse(ocorrencia);
    }

    private void validateEditarOcorrencia(EditarOcorrenciaRequest request){
        Errors erros = new BeanPropertyBindingResult(request, "editarOcorrenciaRequest");
        for(var validator: validatorEditarOcorrencia){
            validator.validate(request, erros);
        }

        for(var validator: validatorCriarOcorrencia){
            validator.validate(request, erros);
        }

        if(erros.hasErrors()){
            throw new ValidacaoNegocioException(erros, "Falha ao preencher o formulário.");
        }
    }
    //endregion

    //region delete
    public void delete(String id) {
        ocorrenciaRepository.deleteById(id);
    }
    //endregion

    //region private methods
    private OcorrenciaResponseBase convertToOcorrenciaBaseResponse(Ocorrencia ocorrencia){
        return new OcorrenciaResponseBase(ocorrencia.getId(), ocorrencia.getPessoaAbertura().getNome(), ocorrencia.getModalidade().getCodigo(), ocorrencia.getDataHoraOcorrencia());
    }

    private OcorrenciaDetalhadoResponse convertToResponseDetalhado(Ocorrencia ocorrencia){
        return new OcorrenciaDetalhadoResponse(ocorrencia.getId(), ocorrencia.getPessoaAbertura().getNome(), ocorrencia.getModalidade().getCodigo(), ocorrencia.getDataHoraOcorrencia(), ocorrencia.getDescricao());
    }
    //endregion
}
