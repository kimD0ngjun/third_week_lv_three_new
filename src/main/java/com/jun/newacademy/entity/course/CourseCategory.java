package com.jun.newacademy.entity.course;

import lombok.Getter;

@Getter
public enum CourseCategory {
    SPRING(CourseCategory.Subject.SPRING),
    REACT(CourseCategory.Subject.REACT),
    NODEJS(CourseCategory.Subject.NODEJS);

    private final String category;

    CourseCategory(String category) {
        this.category = category;
    }

    public static class Subject {
        public static final String SPRING = "SPRING";
        public static final String REACT = "REACT";
        public static final String NODEJS = "NODEJS";
    }
}
