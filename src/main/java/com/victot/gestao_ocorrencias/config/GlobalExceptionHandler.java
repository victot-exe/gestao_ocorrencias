package com.victot.gestao_ocorrencias.config;

import com.victot.gestao_ocorrencias.exceptions.NotAuthException;
import com.victot.gestao_ocorrencias.exceptions.ResourceNotFoundLocalException;
import com.victot.gestao_ocorrencias.exceptions.ValidacaoNegocioException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

    @ExceptionHandler(ResourceNotFoundLocalException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundLocalException ex) {
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(NotAuthException.class)
    public ResponseEntity<Map<String, String>> handleNotAuth(NotAuthException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("erros", ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> mensagens = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        return ResponseEntity.badRequest().body(Map.of("erros", mensagens));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodValidation(HandlerMethodValidationException ex) {
        List<String> mensagens = ex.getAllErrors().stream()
                .map(org.springframework.context.MessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().body(Map.of("erros", mensagens));
    }
}
