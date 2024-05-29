package com.example.demo.profile;

import lombok.Data;

import java.sql.Date;

@Data
public class ProfileEditDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String school;
    private String degree;
    private Date birthDate;


}
