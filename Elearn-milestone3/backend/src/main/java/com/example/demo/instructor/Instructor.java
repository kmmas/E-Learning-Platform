package com.example.demo.instructor;

import com.example.demo.course.Course;
import com.example.demo.systemUser.SystemUser;
import com.example.demo.systemUser.SystemUserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "instructor", uniqueConstraints = {@UniqueConstraint(name = "uk_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_ssn", columnNames = "ssn")})
public class Instructor extends SystemUser {

    @Column(name = "has_privilege", nullable = false)
    @Getter
    @Setter
    private boolean hasPrivilege;

    @Getter
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Course> courses;

    public Instructor() {
        super();
    }

    @Builder
    public Instructor(String firstName, String lastName, String email, String password, String phone, String school,
                      String degree, String ssn, Date birthDate, boolean hasPrivilege) {
        super(firstName, lastName, email, password, phone, school, degree, ssn, birthDate);
        this.hasPrivilege = hasPrivilege;
    }

    @Override
    public List<String> getRole() {
        if (!hasPrivilege) {
            return Collections.singletonList("ROLE_INSTRUCTOR");
        }
        return Arrays.asList("ROLE_INSTRUCTOR", "ROLE_ADMIN");
    }

    @Override
    public SystemUserDto toDto() {
        return new InstructorDto(id, firstName, lastName, email, phone, school, degree, ssn, birthDate, hasPrivilege);
    }

}
