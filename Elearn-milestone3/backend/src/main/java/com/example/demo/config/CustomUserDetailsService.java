package com.example.demo.config;

import com.example.demo.instructor.InstructorService;
import com.example.demo.student.StudentService;
import com.example.demo.systemUser.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentService studentService;
    @Autowired
    private InstructorService instructorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin@admin.com")) {
            return User.builder()
                       .username("admin@admin.com")
                       .password("$2a$10$4uwaYV99.XeHuS/rxzFsEu1hKPuWDLW6YIMDToiyqtUD1e0kxHAGu")
                       .roles("ADMIN")
                       .build();
        }
        SystemUser user;
        try {
            user = studentService.findByEmail(username);
        } catch (UsernameNotFoundException e) {
            user = instructorService.findByEmail(username);
        }
        return new CustomUserDetails(user);
    }

}
