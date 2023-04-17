package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Request.LoginForm;
import com.bizu.querycenter.dto.Response.TokenResponseDto;
import com.bizu.querycenter.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginForm loginForm){
        return ResponseEntity.ok(authService.login(loginForm));
    }

    @GetMapping("/myself")
    public String myself(){
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername().toString();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/method-admin")
    public String method_admin(){
        return "Only admins can see";
    }
}
