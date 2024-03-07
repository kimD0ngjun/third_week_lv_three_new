package com.jun.newacademy.controller;

import com.jun.newacademy.dto.coursedto.CourseRequestDto;
import com.jun.newacademy.dto.coursedto.CourseResponseDto;
import com.jun.newacademy.service.courseservice.CourseService;
import lombok.RequiredArgsConstructor;
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
