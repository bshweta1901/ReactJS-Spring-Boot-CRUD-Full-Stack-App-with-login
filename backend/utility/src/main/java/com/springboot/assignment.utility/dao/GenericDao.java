package com.springboot.assignment.utility.dao;

import java.util.List;

public interface GenericDao<T> {
    List<T> findCustomList(final String query);

    Long countCustomList(final String query);
    T save(final T entity);

    T update(final T entity);

    void customUpdate(final String query);

    String findSingleColumnValue(final String query);

}
