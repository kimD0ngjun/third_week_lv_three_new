package com.jun.newacademy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException {
    private int status;
    private String error;
    private String message;
}
