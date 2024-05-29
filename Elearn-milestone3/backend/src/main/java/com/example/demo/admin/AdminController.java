package com.example.demo.admin;

import com.example.demo.instructor.InstructorDto;
import com.example.demo.instructor.InstructorFilterDetails;
import com.example.demo.systemUser.SystemUserDto;
import com.example.demo.systemUser.SystemUserFilterDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/students")
    public List<SystemUserDto> findStudents(@RequestBody SystemUserFilterDetails filterOptions) {
        return adminService.findStudents(filterOptions);
    }

    @PostMapping("/instructors")
    public List<InstructorDto> findInstructors(@RequestBody InstructorFilterDetails filterOptions) {
        return adminService.findInstructors(filterOptions);
    }

    @PostMapping("/promote/{email}")
    public ResponseEntity<String> promote(@PathVariable String email) {
        try {
            adminService.setPrivilege(email, true);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("no instructor has that email");
        }
        return ResponseEntity.status(200).body("the instructor has been promoted to admin");
    }

    @PostMapping("/demote/{email}")
    public ResponseEntity<String> demote(@PathVariable String email) {
        try {
            adminService.setPrivilege(email, false);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("no instructor has that email");
        }
        return ResponseEntity.status(200).body("the instructor has been demoted");
    }

    @PostMapping("/delete/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        try {
            adminService.deleteUser(email);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("no user has that email");
        }
        return ResponseEntity.status(200).body("the user has been deleted");
    }
}
