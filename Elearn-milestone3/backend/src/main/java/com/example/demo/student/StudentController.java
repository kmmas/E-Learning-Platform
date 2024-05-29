package com.example.demo.student;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.course.Course;
import com.example.demo.course.CourseDto;
import com.example.demo.course.CourseFilterDetails;
import com.example.demo.course.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {
    private final CourseService courseService;
    private final StudentService studentService;

    public StudentController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @PutMapping("/enroll/{courseCode}")
    public ResponseEntity<String> assignCourseToStudent(@AuthenticationPrincipal CustomUserDetails user,
                                                        @PathVariable String courseCode) {
        Course course = courseService.getCourseById(courseCode);
        if (course == null) {
            return ResponseEntity.status(409).body("course doesn't exist");
        }
        return courseService.enrollCourse(courseCode, studentService.findById(user.getSystemUser().getId()));
    }

    @GetMapping("/enrolledCourses")
    public List<CourseDto> getEnrolledCourses(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return courseService.getEnrolledCourses(customUserDetails.getSystemUser().getId()).map(Course::toDto).toList();
    }

    @PostMapping("/availableCourses")
    public List<CourseDto> getUnEnrolledCourses(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                @RequestBody CourseFilterDetails courseFilterDetails) {
        return courseService.getAvailableCourses(customUserDetails.getSystemUser().getId(),
                                                 courseFilterDetails.getCourseName())
                            .map(Course::toDto)
                            .toList();
    }
}
