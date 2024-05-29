package com.example.demo.instructor;

import com.example.demo.filters.DynamicQuery;
import com.example.demo.filters.InstructorFilter;
import com.example.demo.systemUser.SystemUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService extends SystemUserService<Instructor, InstructorRepository> {

    public InstructorService(InstructorRepository repository) {
        super(repository);
    }

    @Override
    public Class<Instructor> getEntityClass() {
        return Instructor.class;
    }

    public List<InstructorDto> findAllBasedOnFilter(InstructorFilterDetails filterDetails) {
        InstructorFilter filter = new InstructorFilter(filterDetails);
        return new DynamicQuery<>(entityManager, getEntityClass()).makeQuery(filter,
                                                                             InstructorDto.class,
                                                                             "id",
                                                                             "firstName",
                                                                             "lastName",
                                                                             "email",
                                                                             "phone",
                                                                             "school",
                                                                             "degree",
                                                                             "ssn",
                                                                             "birthDate",
                                                                             "hasPrivilege");
    }
}
