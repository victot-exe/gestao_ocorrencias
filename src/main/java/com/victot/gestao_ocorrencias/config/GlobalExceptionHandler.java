package com.victot.gestao_ocorrencias.config;

import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidacaoNegocioException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidacaoNegocio(ValidacaoNegocioException ex) {
        List<String> mensagens = ex.getErrors().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(Map.of("erros", mensagens));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(Map.of("erros", mensagens));
    }
}
