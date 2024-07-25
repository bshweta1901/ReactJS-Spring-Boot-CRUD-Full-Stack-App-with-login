package com.springboot.assignment.auth.repository;


import com.springboot.assignment.model_structure.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
