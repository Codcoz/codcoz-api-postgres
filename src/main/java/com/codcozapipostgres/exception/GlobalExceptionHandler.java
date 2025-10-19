package com.codcozapipostgres.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorResponse erro = new ErrorResponse("Erro de execução.",e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse erro = new ErrorResponse("Objeto não encontrado.",e.getMessage(),HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse erro = new ErrorResponse("Parâmetros inválidos.",e.getMessage(),HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException(NumberFormatException e) {
        ErrorResponse erro = new ErrorResponse("Formato inválido.",e.getMessage(),HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
