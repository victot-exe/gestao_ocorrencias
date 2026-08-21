package com.victot.gestao_ocorrencias.exceptions;


public class NotAuthException extends RuntimeException {
    public NotAuthException(String mensage){
        super(mensage);
    }
}
