package com.example.demo.course;

import com.example.demo.instructor.Instructor;
import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "course", uniqueConstraints =
        {@UniqueConstraint(columnNames = "course_code")})
@NoArgsConstructor
public class Course {
    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledCourses")
    Set<Student> studentSet = new HashSet<>();
    @Id
    @Column(name = "course_code")
    private String courseCode;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    private Date deadLine;
    @ManyToOne
    @JoinColumn(name = "instructor")
    private Instructor instructor;


    @Builder
    public Course(String courseCode, String courseName, String description, Date deadline, Instructor instructor) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.deadLine = deadline;
        this.instructor = instructor;
    }

    public CourseDto toDto() {
        return new CourseDto(courseCode,
                             courseName,
                             description,
                             deadLine,
                             instructor.getFirstName(),
                             instructor.getLastName());
    }
}