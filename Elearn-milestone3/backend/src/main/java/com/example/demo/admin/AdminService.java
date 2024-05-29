package com.example.demo.admin;

import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorDto;
import com.example.demo.instructor.InstructorFilterDetails;
import com.example.demo.instructor.InstructorService;
import com.example.demo.student.StudentService;
import com.example.demo.systemUser.SystemUserDto;
import com.example.demo.systemUser.SystemUserFilterDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final StudentService studentService;
    private final InstructorService instructorService;

    public AdminService(StudentService studentService, InstructorService instructorService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    public void setPrivilege(String email, boolean value) {
        Instructor instructor = instructorService.findByEmail(email);
        instructor.setHasPrivilege(value);
        instructorService.saveUser(instructor);
    }

    public List<SystemUserDto> findStudents(SystemUserFilterDetails filterOptions) {
        return studentService.findAllBasedOnFilter(filterOptions);
    }

    public List<InstructorDto> findInstructors(InstructorFilterDetails filterOptions) {
        return instructorService.findAllBasedOnFilter(filterOptions);
    }

    public void deleteUser(String email) {
        try {
            studentService.deleteUser(studentService.findByEmail(email));
        } catch (UsernameNotFoundException e) {
            instructorService.deleteUser(instructorService.findByEmail(email));
        }
    }
}
