package com.jun.newacademy.controller;

import com.jun.newacademy.model.dto.coursedto.CourseRequestDto;
import com.jun.newacademy.model.dto.coursedto.CourseResponseDto;
import com.jun.newacademy.model.entity.user.UserAuthority;
import com.jun.newacademy.service.courseservice.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Transactional
    public CourseResponseDto save(@RequestBody CourseRequestDto requestDto) {
        return courseService.save(requestDto);
    }

    @GetMapping("/{id}")
    public CourseResponseDto findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @Secured(UserAuthority.Role.MANAGER)  // 권한 이름 규칙은 권한 열거형에서 "ROLE_"로 시작해야 함
    @PutMapping("/{id}")
    @Transactional
    public CourseResponseDto update(@PathVariable Long id, @RequestBody CourseRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }

    @GetMapping("/category/{category}")
    public List<CourseResponseDto> findByCategory(@PathVariable String category) {
        return courseService.findByCategory(category);
    }

    @GetMapping("/instructor/{instructorId}")
    public List<CourseResponseDto> findByInstructor(@PathVariable Long instructorId) {
        return courseService.findByInstructor(instructorId);
    }
}
