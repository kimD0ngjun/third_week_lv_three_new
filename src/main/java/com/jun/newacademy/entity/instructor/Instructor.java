package com.jun.newacademy.entity.instructor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String career;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String introduce;
}
