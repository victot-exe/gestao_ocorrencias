package com.victot.gestao_ocorrencias.service.validation;

import org.springframework.validation.Errors;

public interface ValidadorNegocio <T>{
    void validate(T target, Errors errors);
}
