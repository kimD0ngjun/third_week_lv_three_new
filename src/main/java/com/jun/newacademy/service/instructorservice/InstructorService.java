package com.jun.newacademy.service.instructorservice;

import com.jun.newacademy.dto.instructordto.InstructorRequestDto;
import com.jun.newacademy.dto.instructordto.InstructorResponseDto;

public interface InstructorService {
    // 등록
    InstructorResponseDto save(InstructorRequestDto requestDto);

    // 조회
    InstructorResponseDto find(Long id);

    // 수정
    InstructorResponseDto update(Long id, InstructorRequestDto requestDto);
}
