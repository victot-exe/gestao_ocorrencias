package com.victot.gestao_ocorrencias.exceptions;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ValidacaoNegocioException extends RuntimeException{
    private final transient Errors errors;

    public ValidacaoNegocioException(Errors errors, String message){
        super(message);
        this.errors = errors;
    }
}
