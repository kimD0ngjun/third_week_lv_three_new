package com.jun.newacademy.controller;

import com.jun.newacademy.dto.instructordto.InstructorRequestDto;
import com.jun.newacademy.dto.instructordto.InstructorResponseDto;
import com.jun.newacademy.service.instructorservice.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    public InstructorResponseDto save(@RequestBody InstructorRequestDto requestDto) {
        return instructorService.save(requestDto);
    }

    @GetMapping("/{id}")
    public InstructorResponseDto find(@PathVariable Long id) {
        return instructorService.find(id);
    }

    @PutMapping("/{id}")
    public InstructorResponseDto update(@PathVariable Long id, @RequestBody InstructorRequestDto requestDto) {
        return instructorService.update(id, requestDto);
    }
}
