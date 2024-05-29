package com.example.demo.Lecture;

import com.example.demo.course.Course;
import lombok.Data;

import java.io.Serializable;

@Data
public class LectureId implements Serializable {
    private long id;
    private Course course;
}
