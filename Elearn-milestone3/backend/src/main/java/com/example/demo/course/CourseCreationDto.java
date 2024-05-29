package com.example.demo.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseCreationDto {
    private String courseCode;
    private String courseName;
    private String description;
    private Date deadLine;
}
