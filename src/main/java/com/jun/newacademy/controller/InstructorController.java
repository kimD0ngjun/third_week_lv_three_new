package com.jun.newacademy.controller;

import com.jun.newacademy.dto.instructordto.InstructorRequestDto;
import com.jun.newacademy.dto.instructordto.InstructorResponseDto;
import com.jun.newacademy.service.instructorservice.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    @Transactional
    public InstructorResponseDto save(@RequestBody InstructorRequestDto requestDto) {
        return instructorService.save(requestDto);
    }

    @GetMapping("/{id}")
    public InstructorResponseDto find(@PathVariable Long id) {
        return instructorService.find(id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    @Transactional
    public InstructorResponseDto update(@PathVariable Long id, @RequestBody InstructorRequestDto requestDto) {
        return instructorService.update(id, requestDto);
    }
}
