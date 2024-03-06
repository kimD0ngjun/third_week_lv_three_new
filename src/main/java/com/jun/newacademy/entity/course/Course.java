package com.jun.newacademy.entity.course;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Course extends CourseTimestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String syllabus;

    @Column(nullable = false)
    private String category;
}
