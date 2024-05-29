package com.example.demo.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

/**
 * a filter class where user can define predicates that will be used when making the query
 *
 * @param <E> the entity on which the query is to be made
 * @see Predicate
 * @see DynamicQuery
 */
@Data
public abstract class Filter<E> {
    protected String sortBy;
    protected boolean descending;
    protected int firstResult;
    protected int maxResults;

    /**
     * a method that returns the predicate which the user will define
     *
     * @param criteriaBuilder used to construct the predicates
     * @param root            represent the entity table, also needed to construct the predicates
     * @return the predicate defined by user
     */
    public abstract Predicate getPredicates(CriteriaBuilder criteriaBuilder, Root<E> root);

    public Predicate like(CriteriaBuilder criteriaBuilder, Root<E> root, String field, String value) {
        return criteriaBuilder.like(root.get(field), "%" + value + "%");
    }

    public <V> Predicate equal(CriteriaBuilder criteriaBuilder, Root<E> root, String field, V value) {
        return criteriaBuilder.equal(root.get(field), value);
    }

    public Predicate and(CriteriaBuilder criteriaBuilder, Root<E> root, Predicate... predicates) {
        return criteriaBuilder.and(predicates);
    }

    public Predicate or(CriteriaBuilder criteriaBuilder, Root<E> root, Predicate... predicates) {
        return criteriaBuilder.or(predicates);
    }
}
