package com.jun.newacademy.model.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignUpRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 양식을 지켜주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "부서를 입력해주세요.")
    private String department;
}
