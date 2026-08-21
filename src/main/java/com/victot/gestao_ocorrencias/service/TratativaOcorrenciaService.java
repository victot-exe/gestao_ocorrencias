package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.CriarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.EditarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.response.tratativaOcorrencias.TratativaOcorrenciaBaseResponse;
import com.victot.gestao_ocorrencias.entity.TratativaOcorrencia;
import com.victot.gestao_ocorrencias.exceptions.ResourceNotFoundLocalException;
import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.repository.TratativaOcorrenciaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TratativaOcorrenciaService {

    private final TratativaOcorrenciaRepository tratativaOcorrenciaRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final PessoaRepository pessoaRepository;
    private final List<ValidadorNegocio<CriarTratativaOcorrenciaRequest>> validatorCriarTratativaOcorrencia;
    private final List<ValidadorNegocio<EditarTratativaOcorrenciaRequest>> validatorEditarTratativaOcorrencia;

    //region criar
    @Transactional
    public String criar(CriarTratativaOcorrenciaRequest request) {

        validateCriarTrataiva(request);

        var ocorrencia = ocorrenciaRepository.findById(request.getOcorrenciaId())
                .orElseThrow(()-> new ResourceNotFoundLocalException("Ocorrencia não encontrada."));

        var pessoa = pessoaRepository.findById(request.getPessoaAberturaId())
                .orElseThrow(() -> new ResourceNotFoundLocalException("Pessoa não encontrada."));

        var entity = new TratativaOcorrencia(pessoa, ocorrencia, request.getDescricao(), request.getStatusOcorrencia(), request.getDataHoraTratativa());

        ocorrencia.setStatusAtual(request.getStatusOcorrencia());
        var entityDb = tratativaOcorrenciaRepository.save(entity);

        return entityDb.getId();
    }

    private void validateCriarTrataiva(CriarTratativaOcorrenciaRequest request) {
        var errors = new BeanPropertyBindingResult(request, "criarTratativaOcorrencia");

        for (var validator : validatorCriarTratativaOcorrencia) {
            validator.validate(request, errors);
        }

        if(errors.hasErrors()){
            throw new ValidacaoNegocioException(errors, "Falha ao preencher o formulário");
        }
    }
    //endregion

    //region pegeable
    public Page<TratativaOcorrenciaBaseResponse> pegeable(Pageable pageable, String idOcorrencia) {
        return tratativaOcorrenciaRepository.findByOcorrenciaIdPaginado(idOcorrencia, pageable);
    }
    //endregion

    //region editar
    @Transactional
    public String editar(EditarTratativaOcorrenciaRequest request){
        validateEditarTratativa(request);

        var tratativa = tratativaOcorrenciaRepository.findById(request.getIdTratativaOcorrencia())
                .orElseThrow(() -> new ResourceNotFoundLocalException("Tratativa não encontrada."));

        tratativa.setDescricao(request.getDescricao());
        tratativa.setDataHoraTratativa(request.getDataHoraTratativa());

        return tratativa.getId();
    }

    private void validateEditarTratativa(EditarTratativaOcorrenciaRequest request) {
        var errors = new BeanPropertyBindingResult(request, "editarTratativaOcorrencia");

        for (var validator : validatorEditarTratativaOcorrencia) {
            validator.validate(request, errors);
        }

        if(errors.hasErrors()){
            throw new ValidacaoNegocioException(errors, "Falha ao preencher o formulário");
        }
    }
    //endregion

    //region delete
    public void delete(String id){
        tratativaOcorrenciaRepository.deleteById(id);
    }

    //region private
    private TratativaOcorrenciaBaseResponse convertToResponseBase(TratativaOcorrencia entity) {
        return new TratativaOcorrenciaBaseResponse(
                entity.getId(),
                entity.getDescricao(),
                entity.getDataHoraTratativa(),
                entity.getPessoa().getNome(),
                entity.getStatus());
    }
    //endregion
}
