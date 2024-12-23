package com.homework.tricentis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TricentisException extends RuntimeException {
    private final HttpStatus httpStatus; // HTTP Status associated with the error
    private ErrorTypes errorTypes;
}