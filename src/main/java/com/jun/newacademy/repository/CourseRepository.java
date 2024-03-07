package com.jun.newacademy.repository;

import com.jun.newacademy.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);

    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
    List<Course> findByInstructorId(Long instructorId);
}
