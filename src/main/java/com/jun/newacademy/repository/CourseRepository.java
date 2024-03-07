package com.jun.newacademy.repository;

import com.jun.newacademy.model.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // 아이디로 조회
    Optional<Course> findById(Long id);

    // 강사로 조회
    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
    List<Course> findByInstructorId(Long instructorId);

    // 카테고리로 조회
    @Query("SELECT c FROM Course c WHERE c.category = :category")
    List<Course> findByCategory(String category);
}
