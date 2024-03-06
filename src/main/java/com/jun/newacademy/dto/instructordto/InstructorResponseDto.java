package com.jun.newacademy.dto.instructordto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorResponseDto {
    private Long id;
    private String name;
    private String career;
    private String company;
    private String phone;
    private String introduce;
}
