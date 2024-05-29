package com.example.demo.systemUser;

import com.example.demo.filters.DynamicQuery;
import com.example.demo.filters.SystemUserFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class SystemUserService<T extends SystemUser, R extends SystemUserRepository<T>> {

    protected R repository;
    @PersistenceContext
    protected EntityManager entityManager;

    public SystemUserService(R repository) {
        this.repository = repository;
    }

    public void saveUser(T user) {
        repository.save(user);
    }

    public boolean emailTaken(String email) {
        return repository.existsByEmail(email);
    }

    public boolean ssnTaken(String ssn) {
        return repository.existsBySsn(ssn);
    }

    public T findByEmail(String email) throws UsernameNotFoundException {
        Optional<T> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("email isn't registered");
        }
    }

    public T findById(long id) {
        Optional<T> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("no user with that id");
        }
    }

    public void deleteUser(T user) {
        repository.delete(user);
    }

    public abstract Class<T> getEntityClass();


    public <K extends SystemUserFilterDetails> List<SystemUserDto> findAllBasedOnFilter(K filterDetails) {
        SystemUserFilter<T, K> filter = new SystemUserFilter<>(filterDetails);
        return new DynamicQuery<>(entityManager, getEntityClass()).makeQuery(filter,
                                                                             SystemUserDto.class,
                                                                             "id",
                                                                             "firstName",
                                                                             "lastName",
                                                                             "email",
                                                                             "phone",
                                                                             "school",
                                                                             "degree",
                                                                             "ssn",
                                                                             "birthDate");
    }
}
