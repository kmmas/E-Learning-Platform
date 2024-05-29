package com.example.demo.instructor;

import com.example.demo.systemUser.SystemUserDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class InstructorDto extends SystemUserDto {
    @Getter
    @Setter
    private boolean hasPrivilege;

    public InstructorDto(long id,
                         String firstName,
                         String lastName,
                         String email,
                         String phone,
                         String school,
                         String degree,
                         String ssn,
                         Date birthDate,
                         boolean hasPrivilege) {
        super(id, firstName, lastName, email, phone, school, degree, ssn, birthDate);
        this.hasPrivilege = hasPrivilege;
    }
}
