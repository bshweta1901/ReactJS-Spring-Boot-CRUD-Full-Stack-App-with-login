package com.springboot.assignment.auth.service.impl;

import com.springboot.assignment.auth.LoginResponseDto;
import com.springboot.assignment.auth.service.AuthService;
import com.springboot.assignment.model_structure.entity.User;
import com.springboot.assignment.model_structure.payload.LoginDto;
import com.springboot.assignment.auth.security.JwtTokenProvider;
import com.springboot.assignment.utility.dao.GenericDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public User getUserByUsername(String username) {
        List<User> userList = genericDao.findCustomList("from User where username='" + username + "' ");
        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        throw  new UsernameNotFoundException("User not Exist");
    }

    private AuthenticationManager authenticationManager;
    private GenericDao genericDao;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           GenericDao genericDao) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.genericDao=genericDao;
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return new LoginResponseDto(token, authorities);
    }

    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        List<User> userList = genericDao.findCustomList("from User where username='" + username + "' ");
        if (!userList.isEmpty()) {
            return Optional.ofNullable(userList.get(0));
        }
        return Optional.empty();
    }


}
