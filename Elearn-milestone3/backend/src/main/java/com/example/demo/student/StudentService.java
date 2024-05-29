package com.example.demo.student;

import com.example.demo.systemUser.SystemUserService;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends SystemUserService<Student, StudentRepository> {

    public StudentService(StudentRepository repository) {
        super(repository);
    }


    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }

}
