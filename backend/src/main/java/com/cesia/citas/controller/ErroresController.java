package com.cesia.citas.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import java.util.*;
@RestControllerAdvice
public class ErroresController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> negocio(ResponseStatusException e) { return ResponseEntity.status(e.getStatusCode()).body(Map.of("error",Objects.toString(e.getReason(),"Error"))); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validacion(MethodArgumentNotValidException e) {
        List<String> mensajes=e.getBindingResult().getFieldErrors().stream().map(f -> f.getField()+": "+f.getDefaultMessage()).toList();
        return ResponseEntity.badRequest().body(Map.of("error","Datos invalidos","detalles",mensajes));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> relaciones(DataIntegrityViolationException e) { return ResponseEntity.status(409).body(Map.of("error","No se puede realizar la operacion: existen registros relacionados o datos incompatibles")); }
}
