package com.jun.newacademy.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException {
    private String error;
    private String message;
}
