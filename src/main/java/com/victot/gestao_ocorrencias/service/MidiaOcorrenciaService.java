package com.victot.gestao_ocorrencias.service;

import com.victot.gestao_ocorrencias.dtos.request.midiaOcorrencia.MidiaOcorrenciaDto;
import com.victot.gestao_ocorrencias.dtos.response.midias.MidiaDownloadDto;
import com.victot.gestao_ocorrencias.dtos.response.midias.MidiaResponse;
import com.victot.gestao_ocorrencias.entity.MidiaOcorrencia;
import com.victot.gestao_ocorrencias.exceptions.ResourceNotFoundLocalException;
import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import com.victot.gestao_ocorrencias.repository.MidiaOcorrenciaRepository;
import com.victot.gestao_ocorrencias.repository.OcorrenciaRepository;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.storage.StorageService;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MidiaOcorrenciaService {

    private final PessoaRepository pessoaRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final MidiaOcorrenciaRepository midiaOcorrenciaRepository;
    private final StorageService storageService;
    private final List<ValidadorNegocio<MidiaOcorrenciaDto>> validatorMidia;

    //region anexar
    @Transactional
    public MidiaResponse anexar(MidiaOcorrenciaDto dto) {
        validateMidia(dto);

        var ocorrencia = ocorrenciaRepository.findById(dto.getIdOcorrencia())
                .orElseThrow();

        var pessoa = pessoaRepository.findById(dto.getIdPessoa())
                .orElseThrow();

        var nomeArmazenado = storageService.salvar(dto.getArquivo());

        var midia = new MidiaOcorrencia(
                ocorrencia,
                nomeArmazenado,
                pessoa,
                dto.getArquivo().getOriginalFilename(),
                dto.getArquivo().getContentType(),
                dto.getArquivo().getSize(),
                LocalDateTime.now()
                );

        var midiaSalva = midiaOcorrenciaRepository.save(midia);

        return new MidiaResponse(midiaSalva);
    }

    private void validateMidia(MidiaOcorrenciaDto request){
        var errors = new BeanPropertyBindingResult(request, "salvarMidiaOcorrencia");

        for(var validator: validatorMidia){
            validator.validate(request, errors);
        }

        if(errors.hasErrors()){
            throw new ValidacaoNegocioException(errors, "Falha ao preencher o formulário.");
        }
    }
    //endregion

    //region listarPorOcorrencia
    public List<MidiaResponse> listarPorOcorrencia(String idOcorrencia) {
        if (!ocorrenciaRepository.existsById(idOcorrencia)) {
            throw new ResourceNotFoundLocalException("Ocorrência não encontrada.");
        }

        return midiaOcorrenciaRepository.findByOcorrenciaId(idOcorrencia)
                .stream()
                .map(MidiaResponse::new)
                .toList();
    }
    //endregion

    //region buscarParaDownload
    public MidiaDownloadDto buscarParaDownload(String idOcorrencia, String idMidia) {
        var midia = midiaOcorrenciaRepository.findByIdAndOcorrenciaId(idMidia, idOcorrencia)
                .orElseThrow(() -> new ResourceNotFoundLocalException("Mídia não encontrada para esta ocorrência."));

        var resource = storageService.carregar(midia.getNomeArmazenado());

        return new MidiaDownloadDto(resource, midia.getContentType(), midia.getNomeOriginal());
    }
    //endregion

    //region delete
    @Transactional
    public void deletar(String idOcorrencia, String idMidia) {
        var midia = midiaOcorrenciaRepository.findByIdAndOcorrenciaId(idMidia, idOcorrencia)
                .orElseThrow(() -> new ResourceNotFoundLocalException("Mídia não encontrada para esta ocorrência."));

        // 1º Remove do disco físico
        storageService.deletar(midia.getNomeArmazenado());

        // 2º Remove o registro do banco de dados
        midiaOcorrenciaRepository.delete(midia);
    }
    //endregion
}
