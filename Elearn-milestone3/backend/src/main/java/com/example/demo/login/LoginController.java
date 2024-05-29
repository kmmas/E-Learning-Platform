package com.example.demo.login;

import com.example.demo.config.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService cuds;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(),
                                                                    loginRequest.getPassword());
        try {
            List<String> tmp = this.authenticationManager.authenticate(authenticationRequest)
                                                         .getAuthorities()
                                                         .stream()
                                                         .map(GrantedAuthority::getAuthority).toList();
            int page;
            if (tmp.contains("ROLE_STUDENT")) {
                page = 1;
            } else if (tmp.contains("ROLE_INSTRUCTOR") && tmp.contains("ROLE_ADMIN")) {
                page = 3;
            } else if (tmp.contains("ROLE_INSTRUCTOR")) {
                page = 2;
            } else {
                page = 0;
            }
            return ResponseEntity.status(200).body(page);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("the email or password is wrong");
        } catch (LockedException e) {
            return ResponseEntity.status(401).body("the account is locked, contact the admin");
        } catch (DisabledException e) {
            return ResponseEntity.status(401).body("the account is disabled, contact the admin");
        }
    }

    @RequestMapping("/oauth2/signin/{role}")
    public ResponseEntity<Object> Oauth2_login(@PathVariable String role,
                                               @AuthenticationPrincipal OAuth2User oauth2User) {
        System.out.println("output is = " + oauth2User.getAttributes());
        Map<String, Object> data = oauth2User.getAttributes();
        System.out.println("email is = " + data.get("email"));
        System.out.println("role is = " + role);
        try {
            UserDetails ud = cuds.loadUserByUsername(data.get("email").toString());
            System.out.println("user details = " + ud.toString());
            return ResponseEntity.status(200).body("welcome back");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("email has not registered yet");
        }
    }
}
