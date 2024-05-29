package com.example.demo.filters;

import com.example.demo.systemUser.SystemUser;
import com.example.demo.systemUser.SystemUserFilterDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * an implementation of the {@link Filter Filter} class, It handles the common part of student and instructor
 * meaning it responsible for filter their common attribute based on a {@code FilterDetails} object, which will be
 * sent from the front
 *
 * @param <T> the entity class (Student or Instructor)
 * @param <R> the class of {@code FilterDetails} needless to say it must extend {@link SystemUserFilterDetails}
 */
@Getter
@Setter
public class SystemUserFilter<T extends SystemUser, R extends SystemUserFilterDetails> extends Filter<T> {
    protected R systemUserFilterDetails;

    public SystemUserFilter(R systemUserFilterDetails) {
        this.systemUserFilterDetails = systemUserFilterDetails;
        this.sortBy = systemUserFilterDetails.getSortBy() == null ? "firstName" : systemUserFilterDetails.getSortBy();
        this.descending = systemUserFilterDetails.isDescending();
        this.firstResult = systemUserFilterDetails.getFirstResult();
        this.maxResults = systemUserFilterDetails.getMaxResults() == 0 ? 8 : systemUserFilterDetails.getMaxResults();
    }

    @Override
    public Predicate getPredicates(CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (systemUserFilterDetails.getFirstName() != null && !systemUserFilterDetails.getFirstName().isEmpty()) {
            predicates.add(like(criteriaBuilder, root, "firstName", systemUserFilterDetails.getFirstName()));
        }
        if (systemUserFilterDetails.getLastName() != null && !systemUserFilterDetails.getLastName().isEmpty()) {
            predicates.add(like(criteriaBuilder, root, "lastName", systemUserFilterDetails.getLastName()));
        }
        if (systemUserFilterDetails.getDegree() != null && !systemUserFilterDetails.getDegree().isEmpty()) {
            predicates.add(like(criteriaBuilder, root, "degree", systemUserFilterDetails.getDegree()));
        }
        if (systemUserFilterDetails.getPhone() != null && !systemUserFilterDetails.getPhone().isEmpty()) {
            predicates.add(like(criteriaBuilder, root, "phone", systemUserFilterDetails.getPhone()));
        }
        if (systemUserFilterDetails.getEmail() != null && !systemUserFilterDetails.getEmail().isEmpty()) {
            predicates.add(like(criteriaBuilder, root, "email", systemUserFilterDetails.getEmail()));
        }
        if (systemUserFilterDetails.getSsn() != null && !systemUserFilterDetails.getSsn().isEmpty()) {
            predicates.add(like(criteriaBuilder, root, "ssn", systemUserFilterDetails.getSsn()));
        }
        if (systemUserFilterDetails.getSchool() != null && !systemUserFilterDetails.getSchool().isEmpty()) {
            predicates.add(like(criteriaBuilder, root, "school", systemUserFilterDetails.getSchool()));
        }
        if(systemUserFilterDetails.getBirthDate()!=null){
            predicates.add(equal(criteriaBuilder,root,"birthDate",systemUserFilterDetails.getBirthDate()));
        }
        if (systemUserFilterDetails.isOr()) {
            return or(criteriaBuilder, root, predicates.toArray(Predicate[]::new));
        } else {
            return and(criteriaBuilder, root, predicates.toArray(Predicate[]::new));
        }
    }

}
