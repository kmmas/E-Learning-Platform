package com.example.demo.profile;

import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorService;
import com.example.demo.student.Student;
import com.example.demo.student.StudentService;
import com.example.demo.systemUser.SystemUser;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final StudentService studentService;
    private final InstructorService instructorService;

    public ProfileService(StudentService studentService, InstructorService instructorService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    public boolean emailTaken(String email) {
        return studentService.emailTaken(email)
               || instructorService.emailTaken(email)
               || email.equals("admin@admin.com");
    }

    public boolean editProfile(ProfileEditDto data, SystemUser systemUser) {
        if (data.getEmail() != null && !data.getEmail().isEmpty()) {
            if (emailTaken(data.getEmail())) {
                return false;
            } else {
                systemUser.setEmail(data.getEmail());
            }
        }
        if (data.getDegree() != null && !data.getDegree().isEmpty()) {
            systemUser.setDegree(data.getDegree());
        }
        if (data.getPhone() != null && !data.getPhone().isEmpty()) {
            systemUser.setPhone(data.getPhone());
        }
        if (data.getFirstName() != null && !data.getFirstName().isEmpty()) {
            systemUser.setFirstName(data.getFirstName());
        }
        if (data.getLastName() != null && !data.getLastName().isEmpty()) {
            systemUser.setLastName(data.getLastName());
        }
        if (data.getSchool() != null && !data.getSchool().isEmpty()) {
            systemUser.setSchool(data.getSchool());
        }
        if (data.getBirthDate() != null) {
            systemUser.setBirthDate(data.getBirthDate());
        }
        if (systemUser.getRole().contains("ROLE_STUDENT")) {
            studentService.saveUser((Student) systemUser);
        } else if (systemUser.getRole().contains("ROLE_INSTRUCTOR")) {
            instructorService.saveUser((Instructor) systemUser);
        }
        return true;
    }

}
