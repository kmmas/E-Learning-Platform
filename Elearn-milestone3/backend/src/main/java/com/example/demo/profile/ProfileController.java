package com.example.demo.profile;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.systemUser.SystemUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("/profile")
    public SystemUserDto showProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return customUserDetails.getSystemUser().toDto();
    }


    @PostMapping("/profile")
    public ResponseEntity<String> editProfile(@RequestBody ProfileEditDto data,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (profileService.editProfile(data, customUserDetails.getSystemUser())) {
            return ResponseEntity.status(201).body("profile was edited successfully");
        } else {
            return ResponseEntity.status(409).body("the email is taken, the edit was rejected");
        }
    }


}