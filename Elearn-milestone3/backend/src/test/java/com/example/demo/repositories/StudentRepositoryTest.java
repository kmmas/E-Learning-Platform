package com.example.demo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void insertStudent_thenReturnStudentUsingEmail() {
        Student testStudent = Student.builder()
                .firstName("ahmed")
                .lastName("mohamed")
                .email("ahmed@gmail.com")
                .password("test")
                .phone("01127311987")
                .school("high school")
                .degree("PHD")
                .ssn("56728292929")
                .birthDate(Date.valueOf("2023-10-2"))
                .build();
        studentRepository.save(testStudent);
        studentRepository.save(testStudent);
        Optional<Student> tmp = studentRepository.findByEmail("ahmed@gmail.com");
        if (tmp.isPresent()) {
            assertEquals(testStudent, tmp.get());
        } else {
            fail();
        }
    }

    @Test
    public void searchForNonExistingStudent() {
        Optional<Student> tmp = studentRepository.findByEmail("ahmed@gmail.com");
        assert (tmp.isEmpty());
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void insertStudentsWithDuplicateEmails_OnlyTheFirstOneIsSaved() {
        Student testStudent1 = Student.builder()
                .firstName("ahmed")
                .lastName("mohamed")
                .email("ahmed@gmail.com")
                .password("test")
                .phone("01127311987")
                .school("high school")
                .degree("PHD")
                .ssn("56728292929")
                .birthDate(Date.valueOf("2023-10-2"))
                .build();
        Student testStudent2 = Student.builder()
                .firstName("Tom")
                .lastName("mohamed")
                .email("ahmed@gmail.com")
                .password("test")
                .phone("01127311987")
                .school("new high school")
                .degree("PHD")
                .ssn("848488484")
                .birthDate(Date.valueOf("2023-10-2"))
                .build();
        studentRepository.save(testStudent1);

        DataIntegrityViolationException x = assertThrows(DataIntegrityViolationException.class, () -> {
            studentRepository.save(testStudent2);
        });

        assertEquals(x.getRootCause().getMessage(), "Duplicate entry 'ahmed@gmail.com' for key 'student.uk_email'");
        List<Student> students = studentRepository.findAll();

        assertEquals(students.size(), 1);
        assertEquals(students.get(0), testStudent1);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void insertStudentsWithDuplicateSSNs_OnlyTheFirstOneIsSaved() {
        Student testStudent1 = Student.builder()
                .firstName("ahmed")
                .lastName("mohamed")
                .email("ahmed@gmail.com")
                .password("test")
                .phone("01127311987")
                .school("high school")
                .degree("PHD")
                .ssn("56728292929")
                .birthDate(Date.valueOf("2023-10-2"))
                .build();
        Student testStudent2 = Student.builder()
                .firstName("Tom")
                .lastName("mohamed")
                .email("tom@gmail.com")
                .password("test")
                .phone("01127311987")
                .school("new high school")
                .degree("PHD")
                .ssn("56728292929")
                .birthDate(Date.valueOf("2023-10-2"))
                .build();
        studentRepository.save(testStudent1);
        
        DataIntegrityViolationException x = assertThrows(DataIntegrityViolationException.class, () -> {
            studentRepository.save(testStudent2);
        });

        assertEquals(x.getRootCause().getMessage(), "Duplicate entry '56728292929' for key 'student.uk_ssn'");
        List<Student> students = studentRepository.findAll();

        assertEquals(students.size(), 1);
        assertEquals(students.get(0), testStudent1);
    }
}
