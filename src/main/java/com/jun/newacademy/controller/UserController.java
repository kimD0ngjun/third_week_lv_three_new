package com.jun.newacademy.controller;

import com.jun.newacademy.model.dto.userdto.UserSignUpRequestDto;
import com.jun.newacademy.service.userservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/signup")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignUpRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    // * Bean Validation 기능
    // 컨트롤러의 요청 dto 앞에 @Valid 어노테이션 할당해야 가능
    // 유효성 어노테이션 적극적으로 잘 써먹어서 활용해보자
}
