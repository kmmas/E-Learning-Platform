package com.example.demo.instructor;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.course.Course;
import com.example.demo.course.CourseCreationDto;
import com.example.demo.course.CourseDto;
import com.example.demo.course.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/instructor")
public class InstructorController {
    private final InstructorService instructorService;
    private final CourseService courseService;

    public InstructorController(InstructorService instructorService, CourseService courseService) {
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @PostMapping("/addCourse")
    public ResponseEntity<String> addCourse(@AuthenticationPrincipal CustomUserDetails user,
                                            @RequestBody CourseCreationDto courseCreationDto) {
        //check if course already exists
        if (courseService.getCourseById(courseCreationDto.getCourseCode()) != null) {
            return ResponseEntity.status(409).body("the course already exists");
        }
        Instructor instructor = (Instructor) user.getSystemUser();
        Course course = Course.builder()
                              .courseCode(courseCreationDto.getCourseCode())
                              .courseName(courseCreationDto.getCourseName())
                              .deadline(courseCreationDto.getDeadLine())
                              .description(courseCreationDto.getDescription())
                              .instructor(instructor)
                              .build();
        courseService.saveCourseDetails(course);
        return ResponseEntity.status(201).body("course has been added successfully");
    }

    @GetMapping("/getCourses")
    public List<CourseDto> getInstructorCourses(@AuthenticationPrincipal CustomUserDetails user) {
        return instructorService.findById(user.getSystemUser().getId())
                                .getCourses()
                                .stream()
                                .map(Course::toDto)
                                .toList();
    }

    @DeleteMapping("/deleteCourse/{courseCode}")
    public ResponseEntity<String> deleteCourse(@AuthenticationPrincipal CustomUserDetails user,
                                               @PathVariable String courseCode) {
        Course course = courseService.getCourseById(courseCode);
        if (course == null) {
            return ResponseEntity.status(409).body("no course to delete");
        }
        if (course.getInstructor().getId() == user.getSystemUser().getId()) {
            return courseService.deleteCourse(course);
        } else {
            return ResponseEntity.status(403).body("you can't delete a course that isn't yours");
        }
    }
}
