package com.example.demo.Lecture;

import com.example.demo.course.Course;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "lecture")
@IdClass(LectureId.class)
@Data
@NoArgsConstructor
public class Lecture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Id
    @ManyToOne()
    @JoinColumn(name = "course_code", referencedColumnName = "course_code")
    private Course course;


    @Column(name = "video_link", nullable = false)
    private String videoLink;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created", nullable = false)
    private Date date_created;


    public Lecture(Course course, String videoLink, String title, String description, Date date_created) {

        this.course = course;

        this.videoLink = videoLink;
        this.title = title;
        this.description = description;
        this.date_created = date_created;
    }
}


