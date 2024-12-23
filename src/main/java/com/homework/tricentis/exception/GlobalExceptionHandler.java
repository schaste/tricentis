package com.homework.tricentis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TricentisException.class)
    public ResponseEntity<ErrorDetails> handleTricentisException(TricentisException ipe) {
        ErrorDetails errorDetails = new ErrorDetails(ipe.getErrorTypes().getInternalErrorId(), ipe.getErrorTypes().getInternalErrorMessage(), new Date());
        return new ResponseEntity<>(errorDetails, ipe.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
