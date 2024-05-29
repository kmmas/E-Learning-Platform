package com.example.demo.student;


import com.example.demo.course.Course;
import com.example.demo.systemUser.SystemUser;
import com.example.demo.systemUser.SystemUserDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

@Getter
@Setter
@Table(name = "student", uniqueConstraints = {@UniqueConstraint(name = "uk_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_ssn", columnNames = "ssn")})

public class Student extends SystemUser {

    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_code")
    )
    Set<Course> enrolledCourses = new HashSet<>();

    public Student() {
        super();
    }

    @Builder
    public Student(String firstName, String lastName, String email, String password, String phone, String school,
                   String degree, String ssn, Date birthDate) {
        super(firstName, lastName, email, password, phone, school, degree, ssn, birthDate);
    }

    @Override
    public List<String> getRole() {
        return Collections.singletonList("ROLE_STUDENT");
    }

    @Override
    public SystemUserDto toDto() {
        return new SystemUserDto(id, firstName, lastName, email, phone, school, degree, ssn, birthDate);
    }

}
