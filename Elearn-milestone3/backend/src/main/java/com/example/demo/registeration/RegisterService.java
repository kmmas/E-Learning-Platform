package com.example.demo.registeration;

import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorService;
import com.example.demo.student.Student;
import com.example.demo.student.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterService {
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(StudentService studentService,
                           InstructorService instructorService,
                           PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailTaken(String email) {
        return studentService.emailTaken(email)
               || instructorService.emailTaken(email)
               || email.equals("admin@admin.com");
    }

    public boolean ssnTaken(String ssn) {
        return studentService.ssnTaken(ssn) || instructorService.ssnTaken(ssn);
    }

    public void saveUser(RegisterDto registerDTO) {
        if (registerDTO.isStudent()) {
            studentService.saveUser(
                    Student.builder()
                           .firstName(registerDTO.getFirstName())
                           .lastName(registerDTO.getLastName())
                           .email(registerDTO.getEmail())
                           .password(passwordEncoder.encode(registerDTO.getPassword()))
                           .phone(registerDTO.getPhone())
                           .school(registerDTO.getSchool())
                           .degree(registerDTO.getDegree())
                           .ssn(registerDTO.getSsn())
                           .birthDate(registerDTO.getBirthDate())
                           .build());
        } else {
            instructorService.saveUser(
                    Instructor.builder()
                              .firstName(registerDTO.getFirstName())
                              .lastName(registerDTO.getLastName())
                              .email(registerDTO.getEmail())
                              .password(passwordEncoder.encode(registerDTO.getPassword()))
                              .phone(registerDTO.getPhone())
                              .school(registerDTO.getSchool())
                              .degree(registerDTO.getDegree())
                              .ssn(registerDTO.getSsn())
                              .birthDate(registerDTO.getBirthDate())
                              .hasPrivilege(false)
                              .build());
        }
    }

    public void saveUser(String role, OAuth2User oauth2User) {
        Map<String, Object> data = oauth2User.getAttributes();
        if (role.equals("ROLE_STUDENT")) {
            studentService.saveUser(
                    Student.builder()
                           .firstName(data.get("given_name").toString())
                           .lastName(data.get("family_name").toString())
                           .email(data.get("email").toString())
                           .password(passwordEncoder.encode(data.get("at_hash").toString()))
                           .phone(null)
                           .school(null)
                           .degree(null)
                           .ssn(null)
                           .birthDate(null)
                           .build());

        } else if (role.equals("ROLE_INSTRUCTOR")) {
            instructorService.saveUser(
                    Instructor.builder()
                              .firstName(data.get("given_name").toString())
                              .lastName(data.get("family_name").toString())
                              .email(data.get("email").toString())
                              .password(passwordEncoder.encode(data.get("at_hash").toString()))
                              .phone(null)
                              .school(null)
                              .degree(null)
                              .ssn(null)
                              .birthDate(null)
                              .build());
        }
    }


}

