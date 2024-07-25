package com.springboot.assignment.utility.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public abstract class AbstractJpaDao<T> implements GenericDao<T> {

    @PersistenceContext
    EntityManager entityManager;
    private Class<T> clazz;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }


    public T save(T entity) {
        entityManager.persist(entity);
        entityManager.close();
        return entity;
    }

    public T update(T entity) {
        entityManager.merge(entity);
        entityManager.close();
        return entity;
    }

    public List<T> findCustomList(String query) {
        List<T> list = entityManager.createQuery(query).getResultList();
        entityManager.close();
        return list;
    }
    public Long countCustomList(final String query) {
        final Long singleResult = (Long) entityManager.createQuery(query).getSingleResult();
        entityManager.close();
        return singleResult;

    }
    public void customUpdate(final String query) {
        entityManager.createQuery(query).executeUpdate();
        entityManager.close();

    }

    public String findSingleColumnValue(String query) {
        final String singleResult = (String) entityManager.createQuery(query).getSingleResult();
        entityManager.close();
        return singleResult;
    }



}
