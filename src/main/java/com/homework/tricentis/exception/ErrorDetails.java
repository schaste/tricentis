package com.homework.tricentis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private String InternalCode;
    private String message;
    private Date timestamp;
}
