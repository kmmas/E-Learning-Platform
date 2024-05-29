package com.example.demo.Lecture;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.course.Course;
import com.example.demo.course.CourseService;
import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/lecture")
public class LectureController {
    @Autowired
    CourseService cs;
    @Autowired
    InstructorService is;
    @Autowired
    LectureService ls;


    @PostMapping("/create")
    public ResponseEntity<String> createLecture(@AuthenticationPrincipal CustomUserDetails creator,
                                                @RequestBody recieveLectureDTO info) {

        if (!is.emailTaken(creator.getUsername())) {
            return ResponseEntity.status(403).body("Not Authorized to create lectures!");

        }
        Course course = cs.getCourseById(info.getCourseCode());
        Instructor instructor = (Instructor) creator.getSystemUser();
        if (course == null) {
            return ResponseEntity.status(409).body("No course with this code");

        }
        if (!(course.getInstructor().getId() == instructor.getId())) {
            return ResponseEntity.status(403).body("Not Authorized! it is not your course");
        }
        ls.createLecture(info, course);
        return ResponseEntity.status(200).body(" Done! Lecture is added");


    }

    @DeleteMapping("/delete/{cid}/{lid}")
    public ResponseEntity<String> deleteLecture(@AuthenticationPrincipal CustomUserDetails creator,
                                                @PathVariable(name = "cid") String courseCode, @PathVariable(name =
            "lid") long lectureId) {
        if (!is.emailTaken(creator.getUsername())) {
            return ResponseEntity.status(403).body("Not Authorized to delete lectures!");

        }
        Course course = cs.getCourseById(courseCode);
        Instructor instructor = (Instructor) creator.getSystemUser();
        if (course == null) {
            return ResponseEntity.status(409).body("No course with this code");

        }
        if (!(course.getInstructor().getId() == instructor.getId())) {
            return ResponseEntity.status(403).body("Not Authorized! it is not your course");

        }
        if (!ls.deleteLecture(lectureId, course)) {
            return ResponseEntity.status(409).body("No lecture with this id in this course");
        } else {
            return ResponseEntity.status(200).body("Done! Lecture is deleted");

        }

    }

    @GetMapping("/getLectures/{cid}")
    public List<sendLectureDTO> getAllLectures(@PathVariable(name = "cid") String cid) {
        return ls.getAllLectures(cid);


    }

}
