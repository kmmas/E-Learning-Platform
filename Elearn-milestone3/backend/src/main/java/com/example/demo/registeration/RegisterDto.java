package com.example.demo.registeration;

import lombok.Data;

import java.sql.Date;

@Data
public class RegisterDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String school;
    private String degree;
    private String ssn;
    private Date birthDate;
    private boolean student;
}
