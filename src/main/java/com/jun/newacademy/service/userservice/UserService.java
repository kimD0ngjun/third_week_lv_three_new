package com.jun.newacademy.service.userservice;

import com.jun.newacademy.dto.userdto.UserSignUpRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> signUp(UserSignUpRequestDto requestDto);
}
