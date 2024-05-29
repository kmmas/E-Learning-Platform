package com.example.demo.systemUser;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class SystemUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "email")
    protected String email;

    @Column(name = "password")
    protected String password;

    @Column(name = "phone")
    protected String phone;

    @Column(name = "school")
    protected String school;

    @Column(name = "degree")
    protected String degree;

    @Column(name = "ssn")
    protected String ssn;

    @Column(name = "birth_date")
    protected Date birthDate;

    public SystemUser(String firstName, String lastName, String email, String password, String phone, String school,
                      String degree, String ssn, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.school = school;
        this.degree = degree;
        this.ssn = ssn;
        this.birthDate = birthDate;
    }

    public abstract List<String> getRole();

    public abstract SystemUserDto toDto();
}
