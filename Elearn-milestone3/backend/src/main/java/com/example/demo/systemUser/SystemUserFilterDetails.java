package com.example.demo.systemUser;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class SystemUserFilterDetails {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phone;
    protected String school;
    protected String degree;
    protected String ssn;
    protected Date birthDate;
    protected int firstResult;
    protected int maxResults;
    protected String sortBy;
    protected boolean descending;
    protected boolean or;
}
