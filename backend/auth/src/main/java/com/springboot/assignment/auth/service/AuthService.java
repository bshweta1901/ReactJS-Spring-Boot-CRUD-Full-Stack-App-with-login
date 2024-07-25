package com.springboot.assignment.auth.service;

import com.springboot.assignment.auth.LoginResponseDto;
import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.payload.LoginDto;
import com.springboot.assignment.model_structure.payload.RegisterDto;

import java.util.Optional;


public interface AuthService {
    LoginResponseDto login(LoginDto loginDto);

    User getUserByUsername(String username);

    Optional<User> findByUsername(String username) throws Exception;
}
