package com.example.demo.controllers;

import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorService;
import com.example.demo.student.Student;
import com.example.demo.student.StudentService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminControllerTests {
    @Autowired
    StudentService studentService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    private MockMvc mockMvc;

    public void setup() {
        studentService.saveUser(Student.builder()
                                       .firstName("1")
                                       .lastName("mohamed")
                                       .email("ahmed1@gmail.com")
                                       .password("test")
                                       .phone("01127311987")
                                       .school("high school")
                                       .degree("PHD")
                                       .ssn("56728292929")
                                       .birthDate(Date.valueOf("2023-10-2"))
                                       .build());
        studentService.saveUser(Student.builder()
                                       .firstName("2")
                                       .lastName("mohamed")
                                       .email("ahmed2@gmail.com")
                                       .password("test")
                                       .phone("01127311987")
                                       .school("high school")
                                       .degree("PHD")
                                       .ssn("567282929291")
                                       .birthDate(Date.valueOf("2023-10-2"))
                                       .build());
        studentService.saveUser(Student.builder()
                                       .firstName("3")
                                       .lastName("samy")
                                       .email("ahmed3@gmail.com")
                                       .password("test")
                                       .phone("01127311987")
                                       .school("high school")
                                       .degree("PHD")
                                       .ssn("5672829239291")
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
        instructorService.saveUser(Instructor.builder()
                                             .firstName("2")
                                             .lastName("mohamed")
                                             .email("ahmed5@gmail.com")
                                             .password("test")
                                             .phone("01127311987")
                                             .school("high school")
                                             .degree("PHD")
                                             .ssn("1567282929291")
                                             .birthDate(Date.valueOf("2023-10-2"))
                                             .build());
        instructorService.saveUser(Instructor.builder()
                                             .firstName("3")
                                             .lastName("samy")
                                             .email("ahmed6@gmail.com")
                                             .password("test")
                                             .phone("01127311987")
                                             .school("high school")
                                             .degree("PHD")
                                             .ssn("56728292239291")
                                             .birthDate(Date.valueOf("2023-10-2"))
                                             .build());
    }

    /**
     * smoke test for getting all students (with authentication not included in the headers)
     */
    @Test
    @Order(1)
    public void test1() throws Exception {
        setup();
        mockMvc.perform(post("/admin/students").contentType(MediaType.APPLICATION_JSON)
                                               .content("{}")).andExpect(status().is(401));
    }

    /**
     * smoke test for getting all students (with authentication included in the headers)
     */
    @Test
    @Order(2)
    public void test2() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/students").contentType(MediaType.APPLICATION_JSON)
                                               .content("{}").headers(headers)).andExpect(status().is(200));
    }

    /**
     * smoke test for getting all instructors (with authentication not included in the headers)
     */
    @Test
    @Order(3)
    public void test3() throws Exception {
        mockMvc.perform(post("/admin/instructors").contentType(MediaType.APPLICATION_JSON)
                                                  .content("{}")).andExpect(status().is(401));
    }

    /**
     * student
     * test for deleting a user (with authentication included in the headers)
     */
    @Test
    @Order(4)
    public void test4() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/delete/ahmed3@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                              .content("{}").headers(headers))
               .andExpect(status().is(200))
               .andExpect(content().string("the user has been deleted"));
    }

    /**
     * student
     * test for deleting a nonexistent user (the same user deleted before) (with authentication included in the headers)
     */
    @Test
    @Order(5)
    public void test5() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/delete/ahmed3@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                              .content("{}").headers(headers))
               .andExpect(status().is(401))
               .andExpect(content().string("no user has that email"));
    }
    /**
     * instructor
     * test for deleting a user (with authentication included in the headers)
     */
    @Test
    @Order(6)
    public void test6() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/delete/ahmed6@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                              .content("{}").headers(headers))
               .andExpect(status().is(200))
               .andExpect(content().string("the user has been deleted"));
    }

    /**
     * instructor
     * test for deleting a nonexistent user (the same user deleted before) (with authentication included in the headers)
     */
    @Test
    @Order(7)
    public void test7() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/delete/ahmed6@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                              .content("{}").headers(headers))
               .andExpect(status().is(401))
               .andExpect(content().string("no user has that email"));
    }

    /**
     * instructor
     * test for promoting an instructor (with authentication included in the headers)
     */
    @Test
    @Order(8)
    public void test8() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/promote/ahmed4@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                              .content("{}").headers(headers))
               .andExpect(status().is(200))
               .andExpect(content().string("the instructor has been promoted to admin"));
    }
    /**
     * smoke test for getting all instructors (with authentication included in the headers and using one of the promoted instructors)
     */
    @Test
    @Order(9)
    public void test9() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("ahmed4@gmail.com", "admin");
        mockMvc.perform(post("/admin/instructors").contentType(MediaType.APPLICATION_JSON)
                                                  .content("{}").headers(headers)).andExpect(status().is(200));
    }
    /**
     * instructor
     * test for promoting a nonexistent instructor (with authentication included in the headers)
     */
    @Test
    @Order(10)
    public void test10() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/promote/ahmedsssss4@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                               .content("{}").headers(headers))
               .andExpect(status().is(401))
               .andExpect(content().string("no instructor has that email"));
    }
    /**
     * instructor
     * test for demoting an instructor (with authentication included in the headers)
     */
    @Test
    @Order(11)
    public void test11() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/demote/ahmed4@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                               .content("{}").headers(headers))
               .andExpect(status().is(200))
               .andExpect(content().string("the instructor has been demoted"));
    }
    /**
     * instructor
     * test for demoting a nonexistent instructor (with authentication included in the headers)
     */
    @Test
    @Order(12)
    public void test12() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin@admin.com", "admin");
        mockMvc.perform(post("/admin/demote/ahmedsssss4@gmail.com").contentType(MediaType.APPLICATION_JSON)
                                                                    .content("{}").headers(headers))
               .andExpect(status().is(401))
               .andExpect(content().string("no instructor has that email"));
    }


}
