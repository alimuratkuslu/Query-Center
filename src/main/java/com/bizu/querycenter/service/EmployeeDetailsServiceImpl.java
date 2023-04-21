package com.bizu.querycenter.service;

import com.bizu.querycenter.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    private final EmployeeService employeeService;

    public EmployeeDetailsServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee employee = employeeService.getEmployeeByMail(email);

        Set<GrantedAuthority> roles = new HashSet<>();
        employee.getRoles().forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new User(
                employee.getEmail(),
                employee.getPassword(),
                roles);

    }
}
