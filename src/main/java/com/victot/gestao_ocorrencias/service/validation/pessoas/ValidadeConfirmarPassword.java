package com.victot.gestao_ocorrencias.service.validation.pessoas;

import com.victot.gestao_ocorrencias.dtos.request.pessoas.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.service.validation.ValidadorNegocio;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ValidadeConfirmarPassword implements ValidadorNegocio<CriarPessoaRequest> {
    @Override
    public void validate(CriarPessoaRequest target, Errors errors) {
        if(!target.getPassword().equals(target.getConfirmPassword())){
            errors.rejectValue("password", "senha_nao_confirmada", "As senhas não são iguais.");
        }
    }
}
