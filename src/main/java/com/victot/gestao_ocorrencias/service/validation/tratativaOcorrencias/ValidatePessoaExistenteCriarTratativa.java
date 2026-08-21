package com.victot.gestao_ocorrencias.service.validation.tratativaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.CriarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class ValidatePessoaExistenteCriarTratativa implements ValidadorNegocio<CriarTratativaOcorrenciaRequest> {

    private final PessoaRepository pessoaRepository;

    @Override
    public void validate(CriarTratativaOcorrenciaRequest target, Errors errors) {
        var existe = pessoaRepository.existsById(target.getPessoaAberturaId());

        if(existe) return;

        errors.rejectValue("pessoaAberturaId", "pessoaAberturaId.inexistente", "Não há uma pessoa com este Id cadastrado.");
    }
}
