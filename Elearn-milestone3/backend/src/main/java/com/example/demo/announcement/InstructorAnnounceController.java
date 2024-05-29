package com.example.demo.announcement;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.instructor.Instructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/announce")
public class InstructorAnnounceController {


    private final AnnouncementServices announcementServices;

    public InstructorAnnounceController(AnnouncementServices announcementServices) {
        this.announcementServices = announcementServices;
    }

    @PostMapping("/addAnnounce")
    public ResponseEntity<String> addAnnounce(@AuthenticationPrincipal CustomUserDetails user,
                                              @RequestBody AnnouncementDTO announcementDTO) {
        if (!(user.getSystemUser() instanceof Instructor instructor)) {
            return ResponseEntity.status(401).body("not an instructor");
        }
        return announcementServices.addAnnounce(instructor, announcementDTO);
    }

    @PostMapping("/deleteAnnounce")
    public ResponseEntity<String> deleteAnnounce(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody String announceName) {

        return announcementServices.deleteAnnounce(announceName);

    }

    @GetMapping("/getAllAnnounce/{courseCode}")
    public List<Announcement> getAllAnnouncements(@PathVariable String courseCode) {
        System.out.println("courseCode:"+courseCode);
        return announcementServices.getAllAnnounceOfCourse(courseCode);
    }


}
