package com.example.demo.course;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class CourseDto {
    private String courseCode;
    private String courseName;
    private String description;
    private Date deadLine;
    private String instructorFirstName;
    private String instructorLastName;
}
