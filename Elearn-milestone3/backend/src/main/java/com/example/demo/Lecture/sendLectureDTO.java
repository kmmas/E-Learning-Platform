package com.example.demo.Lecture;


import lombok.Data;

@Data
public class sendLectureDTO {
    Long lid;
    String courseCode;
    String title;
    String description;
    String videoLink;

}