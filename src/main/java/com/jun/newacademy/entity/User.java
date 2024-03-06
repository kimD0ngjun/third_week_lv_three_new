package com.jun.newacademy.entity;

mport jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String authority;

    public User(String email, String password, String department, String authority) {
        this.email = email;
        this.password = password;
        this.department = department;
        this.authority = authority;
    }
}
