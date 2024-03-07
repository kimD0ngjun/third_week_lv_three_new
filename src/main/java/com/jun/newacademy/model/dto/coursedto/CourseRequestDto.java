package com.jun.newacademy.model.dto.coursedto;

import lombok.Getter;

@Getter
public class CourseRequestDto {
    private String title;
    private String price;
    private String syllabus;
    private String category;
    private Long instructorId;
}
