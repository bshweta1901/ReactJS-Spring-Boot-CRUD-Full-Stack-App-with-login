package com.springboot.assignment.auth;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class LoginResponseDto {
    private String token;
    private Collection<? extends GrantedAuthority> authorities;

    public LoginResponseDto(String token, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
