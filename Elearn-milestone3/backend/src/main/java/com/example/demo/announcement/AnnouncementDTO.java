package com.example.demo.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class AnnouncementDTO {
    private String announcementName;
    private String description;
    private String courseCode;

}
