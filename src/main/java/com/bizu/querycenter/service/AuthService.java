package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Request.LoginForm;
import com.bizu.querycenter.dto.Response.TokenResponseDto;
import com.bizu.querycenter.utils.TokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final EmployeeService employeeService;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;

    public AuthService(EmployeeService employeeService, TokenGenerator tokenGenerator, AuthenticationManager authenticationManager) {
        this.employeeService = employeeService;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
    }

    // Gets the logged in user's email
    public String getLoggedInEmail(){
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public TokenResponseDto login(LoginForm loginForm){

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

        return TokenResponseDto.builder()
                .accessToken(tokenGenerator.generateToken(auth))
                //.response(userService.getUserByEmail(loginForm.getEmail()))
                .build();
    }
}
