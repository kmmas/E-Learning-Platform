package com.example.demo.instructor;

import com.example.demo.systemUser.SystemUserFilterDetails;
import lombok.Getter;
import lombok.Setter;


public class InstructorFilterDetails extends SystemUserFilterDetails {
    @Getter
    @Setter
    int hasPrivilege;
}
