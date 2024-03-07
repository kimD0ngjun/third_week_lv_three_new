package com.jun.newacademy.dto.coursedto;

import com.jun.newacademy.entity.course.Course;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseResponseDto {
    private Long id;
    private String title;
    private String price;
    private String syllabus;
    private String category;
    private Long instructorId;
    private String registration;

    public static CourseResponseDto fromEntity(Course course) {
        CourseResponseDto courseDTO = new CourseResponseDto();

        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setSyllabus(course.getSyllabus());
        courseDTO.setCategory(course.getCategory().toString());
        courseDTO.setInstructorId(course.getInstructor().getId()); // 외래키인 instructorId 설정
        courseDTO.setRegistration(course.getCreatedAt().toString()); // timestamped 필드인 registration 설정

        return courseDTO;
    }
}
