package com.portifolio.api_transacao.core.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        log.error("Transaction error: ", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
    }


    @ExceptionHandler({JsonParseException.class, InvalidFormatException.class})
    public ResponseEntity<String> handleJsonParseExceptions(Exception ex) {
        log.error("Transaction error: ", ex);
        return ResponseEntity.badRequest().body("Invalid JSON format: " + ex.getMessage());
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<String> handleUnknownProperty(UnrecognizedPropertyException ex) {
        String unknownField = ex.getPropertyName();
        String message = "Unknown property in JSON: " + unknownField;
        return ResponseEntity.badRequest().body(message);
    }

}
