package com.victot.gestao_ocorrencias.service.validation.midiaOcorrencias;

import com.victot.gestao_ocorrencias.dtos.request.midiaOcorrencia.MidiaOcorrenciaDto;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("validarPessoaExistenteParaMidia")
@RequiredArgsConstructor
public class ValidarPessoaExistente implements ValidadorNegocio<MidiaOcorrenciaDto> {

    private final PessoaRepository pessoaRepository;

    @Override
    public void validate(MidiaOcorrenciaDto target, Errors errors) {
        var existe = pessoaRepository.existsById(target.getIdPessoa());

        if(existe) return;

        errors.rejectValue("idPessoa", "idPessoa.inexistente", "Não há uma pessoa com este Id cadastrado.");
    }
}
