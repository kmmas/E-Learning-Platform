package com.example.demo.controllers;

import com.example.demo.admin.AdminService;
import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorService;
import com.example.demo.student.Student;
import com.example.demo.student.StudentService;
import com.example.demo.utilities.Helper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class LoginControllerTests {
    @Autowired
    private StudentService studentService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MockMvc mockMvc;
    private final String path = "src/test/resources/loginTestFiles/";

    public void setUp() {
        studentService.saveUser(Student.builder()
                .firstName("ahmed")
                .lastName("mohamed")
                .email("ahmed@gmail.com")
                .password("$2a$10$4uwaYV99.XeHuS/rxzFsEu1hKPuWDLW6YIMDToiyqtUD1e0kxHAGu")
                .phone("01127311987")
                .school("high school")
                .degree("PHD")
                .ssn("56728292929")
                .birthDate(Date.valueOf("2023-10-2"))
                .build());
        instructorService.saveUser(Instructor.builder()
                .firstName("ahmed")
                .lastName("samy")
                .email("samy@gmail.com")
                .password("$2a$10$4uwaYV99.XeHuS/rxzFsEu1hKPuWDLW6YIMDToiyqtUD1e0kxHAGu")
                .phone("01127311987")
                .school("private high school")
                .degree("PHD")
                .ssn("78728728")
                .birthDate(Date.valueOf("2023-10-2"))
                .hasPrivilege(false)
                .build());
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void test1() throws Exception {
        setUp();
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(Helper.readFile(path + "test1.json"))).andExpect(status().isOk()).andExpect(content().string("0"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void test2() throws Exception {
        setUp();
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(Helper.readFile(path + "test2.json"))).andExpect(status().isOk()).andExpect(content().string("1"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void test3() throws Exception {
        setUp();
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(Helper.readFile(path + "test3.json"))).andExpect(status().isOk()).andExpect(content().string("2"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void test4() throws Exception {
        setUp();
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(Helper.readFile(path + "test4.json"))).andExpect(status().is(401)).andExpect(content().string("the email or password is wrong"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void test5() throws Exception {
        setUp();
        adminService.setPrivilege("samy@gmail.com",true);
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(Helper.readFile(path + "test3.json"))).andExpect(status().isOk()).andExpect(content().string("3"));
    }
}
