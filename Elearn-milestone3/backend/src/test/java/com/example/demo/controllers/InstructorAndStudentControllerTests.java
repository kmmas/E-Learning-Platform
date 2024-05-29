package com.example.demo.controllers;

import com.example.demo.course.Course;
import com.example.demo.course.CourseService;
import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorService;
import com.example.demo.student.Student;
import com.example.demo.student.StudentService;
import com.example.demo.utilities.Helper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InstructorAndStudentControllerTests {
    @Autowired
    StudentService studentService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    CourseService courseService;
    @Autowired
    private MockMvc mockMvc;
    private final String path = "src/test/resources/instructorControllerTestFiles/";

    public void setup() {
        studentService.saveUser(Student.builder()
                                       .firstName("1")
                                       .lastName("mohamed")
                                       .email("ahmed1@gmail.com")
                                       .password("$2a$10$4uwaYV99.XeHuS/rxzFsEu1hKPuWDLW6YIMDToiyqtUD1e0kxHAGu")
                                       .phone("01127311987")
                                       .school("high school")
                                       .degree("PHD")
                                       .ssn("56728292929")
                                       .birthDate(Date.valueOf("2023-10-2"))
                                       .build());
        instructorService.saveUser(Instructor.builder()
                                             .firstName("1")
                                             .lastName("mohamed")
                                             .email("ahmed4@gmail.com")
                                             .password("$2a$10$4uwaYV99.XeHuS/rxzFsEu1hKPuWDLW6YIMDToiyqtUD1e0kxHAGu")
                                             .phone("01127311987")
                                             .school("high school")
                                             .degree("PHD")
                                             .ssn("5672228292929")
                                             .birthDate(Date.valueOf("2023-10-2"))
                                             .build());
    }

    /**
     * adding course without headers
     */
    @Test
    @Order(1)
    public void test1() throws Exception {
        setup();
        mockMvc.perform(post("/instructor/addCourse").contentType(MediaType.APPLICATION_JSON)
                                                     .content(Helper.readFile(path+"test1.json"))).andExpect(status().is(401));
    }

    /**
     * adding course using student account
     */
    @Test
    @Order(2)
    public void test2() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed1@gmail.com", "admin");
        mockMvc.perform(post("/instructor/addCourse").contentType(MediaType.APPLICATION_JSON)
                                                     .content(Helper.readFile(path+"test1.json")).headers(headers)).andExpect(status().is(403));
    }

    /**
     * adding course using an instructor account
     */
    @Test
    @Order(3)
    public void test3() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed4@gmail.com", "admin");
        mockMvc.perform(post("/instructor/addCourse").contentType(MediaType.APPLICATION_JSON)
                                                     .content(Helper.readFile(path+"test1.json")).headers(headers)).andExpect(status().is(201));
    }

    /**
     * adding a course with the same existing course code as an existing course
     */
    @Test
    @Order(4)
    public void test4() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed4@gmail.com", "admin");
        mockMvc.perform(post("/instructor/addCourse").contentType(MediaType.APPLICATION_JSON)
                                                     .content(Helper.readFile(path+"test1.json")).headers(headers)).andExpect(status().is(409));
    }

    /**
     * getting courses of an instructor
     */
    @Test
    @Order(5)
    public void test5() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed4@gmail.com", "admin");
        mockMvc.perform(get("/instructor/getCourses").headers(headers)).andExpect(status().is(200));
    }

    @Test
    @Order(6)
    public void test6() throws Exception {
        List<Course> available = courseService.getAvailableCourses(1,"").toList();
        assertEquals(available.size(),1);
        List<Course> enrolled = courseService.getEnrolledCourses(1).toList();
        assertEquals(enrolled.size(),0);
    }
    @Test
    @Order(7)
    public void test7() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed1@gmail.com", "admin");
        mockMvc.perform(put("/student/enroll/MAT").headers(headers)).andExpect(status().is(200));

    }
    @Test
    @Order(8)
    public void test8() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed1@gmail.com", "admin");
        mockMvc.perform(put("/student/enroll/LLL").headers(headers)).andExpect(status().is(409));

    }
    @Test
    @Order(9)
    public void test9() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed1@gmail.com", "admin");
        mockMvc.perform(put("/student/enroll/MAT").headers(headers)).andExpect(status().is(400));

    }
    @Test
    @Order(10)
    public void test10() throws Exception {
        List<Course> available = courseService.getAvailableCourses(1,"").toList();
        assertEquals(available.size(),0);
        List<Course> enrolled = courseService.getEnrolledCourses(1).toList();
        assertEquals(enrolled.size(),1);
    }
    @Test
    @Order(11)
    public void test11() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed1@gmail.com", "admin");
        mockMvc.perform(get("/student/enrolledCourses").headers(headers)).andExpect(status().is(200));
    }@Test
    @Order(12)
    public void test12() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed1@gmail.com", "admin");
        mockMvc.perform(post("/student/availableCourses").contentType(MediaType.APPLICATION_JSON).content("{}").headers(headers)).andExpect(status().is(200));
    }
}
