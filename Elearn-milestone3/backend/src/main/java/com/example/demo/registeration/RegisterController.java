package com.example.demo.registeration;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody RegisterDto registerDTO) {

        if (registerService.emailTaken(registerDTO.getEmail())) {
            return ResponseEntity.status(409).body("email is taken");
        }
        if (registerService.ssnTaken(registerDTO.getSsn())) {
            return ResponseEntity.status(409).body("there is another user with the same SSN");
        }
        registerService.saveUser(registerDTO);
        return ResponseEntity.status(201).body("registered successfully");
    }

    @RequestMapping("/oauth2/{role}")
    public ResponseEntity<Object> googleRegister(@PathVariable String role,
                                                 @AuthenticationPrincipal OAuth2User oauth2User) {

        if (registerService.emailTaken(oauth2User.getAttributes().get("email").toString())) {
            return ResponseEntity.status(409).body("email is taken");
        }
        registerService.saveUser(role, oauth2User);
        return ResponseEntity.status(200).body("registered successfully");
    }

}
