package com.springboot.assignment.utility.dao;

import org.springframework.stereotype.Repository;

@Repository("commonGenericDao")
public class GenericDaoImpl< T >
extends AbstractJpaDao< T >{



}
