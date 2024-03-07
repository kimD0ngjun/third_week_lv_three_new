package com.jun.newacademy.service.courseservice;

import com.jun.newacademy.dto.coursedto.CourseRequestDto;
import com.jun.newacademy.dto.coursedto.CourseResponseDto;

import java.util.List;

public interface CourseService {
    // 강의 등록
    CourseResponseDto save(CourseRequestDto requestDto);

    // 강의 조회
    CourseResponseDto findById(Long id);

    // 강의 수정
    CourseResponseDto update(Long id);

    // 강의 카테고리별 조회
    List<CourseResponseDto> findByCategory(String category);

    // 강의 강사별 조회
    List<CourseResponseDto> findByInstructor(Long instructorId);
}
