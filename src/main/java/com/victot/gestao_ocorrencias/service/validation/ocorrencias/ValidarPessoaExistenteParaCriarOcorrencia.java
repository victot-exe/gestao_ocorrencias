package com.victot.gestao_ocorrencias.service.validation.ocorrencias;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidarPessoaExistenteParaCriarOcorrencia implements ValidadorNegocio<CriarOcorrenciaRequest> {
    private final PessoaRepository pessoaRepository;

    @Override
    public void validate(CriarOcorrenciaRequest target, Errors errors) {
        var existe = pessoaRepository.existsById(target.getPessoaAberturaId());

        if(existe) return;

        errors.rejectValue("pessoaAberturaId", "id.inexistente", "Não uma pessoa com este Id cadastrado.");
    }
}
