package com.jun.newacademy.controller;

import com.jun.newacademy.model.dto.userdto.UserSignUpRequestDto;
import com.jun.newacademy.service.userservice.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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

//
//    @PostMapping("/user/signup")
//    public String signup(@Valid UserSignupRequestDto requestDto, BindingResult bindingResult) {
//
//        // BindingResult 객체에는 Validation에서 오류가 발생할 경우 오류에 대한 정보가 담겨서 들어옴
//        // Validation 예외처리
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors(); //
//        if(fieldErrors.size() > 0) { // 에러가 하나라도 있으면
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
//            }
//            return "redirect:/api/user/signup"; // 다시 회원가입 정적 페이지로 리다이렉팅
//        }
//
//        userService.signup(requestDto);
//
//        return "redirect:/api/user/login-page";
//    }

}

