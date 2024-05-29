package com.example.demo.systemUser;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class SystemUserDto {
    protected long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phone;
    protected String school;
    protected String degree;
    protected String ssn;
    protected Date birthDate;
}
