package com.jun.newacademy.repository;

import com.jun.newacademy.entity.instructor.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findById(Long id);
}
