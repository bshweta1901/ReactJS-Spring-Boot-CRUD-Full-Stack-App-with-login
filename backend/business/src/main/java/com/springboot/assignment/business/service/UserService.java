package com.springboot.assignment.business.service;

import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.payload.LoginDto;
import com.springboot.assignment.model_structure.payload.RegisterDto;

import java.util.List;

public interface UserService  {

    String changePassword(User user) throws Exception;

    User getUserByUsername(String username) throws Exception;

    List<User> getUserList(User user) throws Exception;

    User registerUser(User user) throws Exception;

    void update(User user) throws Exception;

    Object getUserCount(User user) throws Exception;

    User getUserById(Long id) throws Exception;

    void changeStatus(Long id) throws Exception;

    void deleteUser(Long id) throws Exception;
}
