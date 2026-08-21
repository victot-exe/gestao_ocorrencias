package com.victot.gestao_ocorrencias.exceptions;

public class ResourceNotFoundLocalException extends RuntimeException {
    public ResourceNotFoundLocalException(String message) {
        super(message);
    }
}