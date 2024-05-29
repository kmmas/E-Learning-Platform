package com.example.demo.announcement;

import com.example.demo.course.Course;
import com.example.demo.instructor.Instructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "announcement", uniqueConstraints =
        {@UniqueConstraint(columnNames = "announce_name")})
public class Announcement {
    @Id
    @Column(name = "announce_name")
    private String announcementName;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_code")
    private Course course;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

}
