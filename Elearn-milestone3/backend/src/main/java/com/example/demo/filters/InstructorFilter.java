package com.example.demo.filters;

import com.example.demo.instructor.Instructor;
import com.example.demo.instructor.InstructorFilterDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class InstructorFilter extends SystemUserFilter<Instructor, InstructorFilterDetails> {
    public InstructorFilter(InstructorFilterDetails systemUserFilterDetails) {
        super(systemUserFilterDetails);
    }

    @Override
    public Predicate getPredicates(CriteriaBuilder criteriaBuilder, Root<Instructor> root) {
        Predicate predicate = super.getPredicates(criteriaBuilder, root);
        switch (this.systemUserFilterDetails.getHasPrivilege()) {
            case 1:
                predicate = and(criteriaBuilder, root, predicate, equal(criteriaBuilder, root, "hasPrivilege", false));
                break;
            case 2:
                predicate = and(criteriaBuilder, root, predicate, equal(criteriaBuilder, root, "hasPrivilege", true));
                break;
            default:
                break;
        }
        return predicate;
    }
}
