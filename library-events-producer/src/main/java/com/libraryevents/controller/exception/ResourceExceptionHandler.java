package com.libraryevents.controller.exception;

import com.libraryevents.exceptions.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<StandarError> validEntity(ValidateException e , HttpServletRequest req){

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandarError erro = new StandarError(System.currentTimeMillis(), status.value(), "Valores incorretos", e.getMessage(), req.getRequestURI());

        return ResponseEntity.status(status).body(erro);
    }
}