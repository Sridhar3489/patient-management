package com.patient.patient_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //Cross Cutting Concerns
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error->errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> emailExists(EmailAlreadyExistsException emailAlreadyExistsException){
        Map<String, String> errors = new HashMap<>();
        log.warn("Email already exists with this {}",  emailAlreadyExistsException.getMessage());
        errors.put("message", "Email already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> patientNotFound(PatientNotFoundException patientNotFoundException){
        log.warn("Patient not found with ID {}", patientNotFoundException.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Patient not found with the specified ID");
        return ResponseEntity.badRequest().body(errors);
    }

}
