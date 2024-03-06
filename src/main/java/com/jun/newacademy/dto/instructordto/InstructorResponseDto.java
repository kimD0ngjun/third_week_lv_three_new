package com.jun.newacademy.dto.instructordto;

import com.jun.newacademy.entity.instructor.Instructor;
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

    public InstructorResponseDto(Instructor instructor) {
        this.id = instructor.getId();
        this.name = instructor.getName();
        this.career = instructor.getCareer();
        this.company = instructor.getCompany();
        this.phone = instructor.getPhone();
        this.introduce = instructor.getIntroduce();
    }
}
