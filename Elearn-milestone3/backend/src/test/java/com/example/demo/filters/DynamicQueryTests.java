package com.example.demo.filters;

import com.example.demo.instructor.InstructorService;
import com.example.demo.student.Student;
import com.example.demo.student.StudentService;
import com.example.demo.systemUser.SystemUserFilterDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DynamicQueryTests {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    StudentService studentService;
    @Autowired
    InstructorService instructorService;

    public void studentSetup() {
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
    }

    /**
     * simply test for basic query without any filtering using mapping
     */
    @Test
    @Order(1)
    public void test1() {
        studentSetup();
        SystemUserFilterDetails systemUserFilterDetails = new SystemUserFilterDetails();
        List<String> x = new DynamicQuery<Student>(entityManager, Student.class)
                .makeQuery(new SystemUserFilter<>(systemUserFilterDetails), Student::getFirstName);
        assertEquals(x.size(),3);
        assertEquals(x.get(0),"1");
        assertEquals(x.get(1),"2");
        assertEquals(x.get(2),"3");
    }

    /**
     * simply test for basic query with using or filter using mapping
     */
    @Test
    @Order(2)
    public void test2() {
        SystemUserFilterDetails systemUserFilterDetails = new SystemUserFilterDetails();
        systemUserFilterDetails.setOr(true);
        List<String> x = new DynamicQuery<Student>(entityManager, Student.class)
                .makeQuery(new SystemUserFilter<>(systemUserFilterDetails), Student::getFirstName);
        assertEquals(x.size(),0);
    }

    /**
     * search for a none existent student, note we removed or filter
     */
    @Test
    @Order(3)
    public void test3() {
        SystemUserFilterDetails systemUserFilterDetails = new SystemUserFilterDetails();
        systemUserFilterDetails.setFirstName("ahmed");
        systemUserFilterDetails.setEmail("ahmed");
        systemUserFilterDetails.setDegree("ahmed");
        systemUserFilterDetails.setPhone("ahmed");
        systemUserFilterDetails.setLastName("ahmed");
        systemUserFilterDetails.setSchool("ahmed");
        systemUserFilterDetails.setSsn("ahmed");
        systemUserFilterDetails.setDescending(true);
        List<String> x = new DynamicQuery<Student>(entityManager, Student.class)
                .makeQuery(new SystemUserFilter<>(systemUserFilterDetails), Student::getFirstName);
        assertEquals(x.size(),0);
    }
}
