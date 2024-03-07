package com.jun.newacademy.entity.course;

import com.jun.newacademy.dto.coursedto.CourseRequestDto;
import com.jun.newacademy.entity.instructor.Instructor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "courses")
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
    private CourseCategory category;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id", nullable = false)
    private Instructor instructor;

    public Course(CourseRequestDto requestDto, Instructor instructor, CourseCategory category) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.syllabus = requestDto.getSyllabus();
        this.category = category;
        this.instructor = instructor;
    }
}
