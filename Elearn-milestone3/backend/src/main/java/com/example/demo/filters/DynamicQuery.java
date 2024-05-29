package com.example.demo.filters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * a class for making database queries on some entity, this query is made dynamically based on the provide filter
 *
 * @param <T> the entity on the query to be made
 * @see Filter
 * @see CriteriaQuery
 */
public class DynamicQuery<T> {
    private final EntityManager entityManager;
    private final Class<T> entityClass;

    public DynamicQuery(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    /**
     * a method that makes query on some entity based on a filter then map the result based on the functional
     * interface {@code mapper}
     *
     * @param filter the filter which defines the predicates to be used in the query
     * @param mapper a functional interface that maps between the entity and another type
     * @param <R>    the output type of {@code mapper}
     * @return an immutable list containing the result of the query after mapping, its type is the output type of
     * {@code mapper}
     */
    public <R> List<R> makeQuery(Filter<T> filter, Function<T, R> mapper) {
        return makeQuery(filter).stream().map(mapper).toList();
    }

    /**
     * a method that makes query on some entity based on a filter
     *
     * @param filter the filter which defines the predicates to be used in the query
     * @return a list containing the result of the query
     */
    public List<T> makeQuery(Filter<T> filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        String sortBy = filter.getSortBy();
        if (sortBy != null) {
            if (filter.isDescending()) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            }
        }
        criteriaQuery.select(root).where(filter.getPredicates(criteriaBuilder, root));
        return entityManager.createQuery(criteriaQuery)
                            .setMaxResults(filter.getMaxResults())
                            .setFirstResult(filter.getFirstResult())
                            .getResultList();
    }

    /**
     * a method that make a query on some entity to find the count based on a filter
     *
     * @param filter the filter which defines the predicates to be used in the query
     * @return the count of records matching that predicate in the entity
     */
    public long getCount(Filter<T> filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(root)).where(filter.getPredicates(criteriaBuilder, root));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    /**
     * a method that makes query on some entity based on a filter but the result is a list of {@code outputClass}
     * so it is similar to {@link #makeQuery(Filter, Function)} but instead the mapping is in the query not after and
     * using {@link CriteriaBuilder#construct(Class, Selection[]) CriteriaBuilder.construct}.
     * <p>
     * I made it since there is a possibility that it gives a better performance than
     * {@link #makeQuery(Filter, Function)} (not that it performed bad or anything, but in theory this method should
     * reduce the number of object created by half, since instead of using the returned list of a certain type then
     * mapping each one to another, we just return a list of our wanted type from the start).
     *
     * @param filter      the filter which defines the predicates to be used in the query
     * @param outputClass the output class
     * @param fields      the name of fields from the entity class to be given to the constructor of the output
     *                    class, meaning there must be a constructor in the output class that support the fields in
     *                    the entered order or an exception will be thrown
     * @param <R>         the output type
     * @return a list containing the result of the query mapped to the output type
     */
    public <R> List<R> makeQuery(Filter<T> filter, Class<R> outputClass, String... fields) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<R> criteriaQuery = criteriaBuilder.createQuery(outputClass);
        Root<T> root = criteriaQuery.from(entityClass);
        String sortBy = filter.getSortBy();
        if (sortBy != null) {
            if (filter.isDescending()) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            }
        }
        criteriaQuery.select(criteriaBuilder.construct(outputClass, Arrays.stream(fields)
                                                                          .map(root::get)
                                                                          .toArray(Selection[]::new)));
        criteriaQuery.where(filter.getPredicates(criteriaBuilder, root));
        return entityManager.createQuery(criteriaQuery)
                            .setMaxResults(filter.getMaxResults())
                            .setFirstResult(filter.getFirstResult())
                            .getResultList();
    }


}
