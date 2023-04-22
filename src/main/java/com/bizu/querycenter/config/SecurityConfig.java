package com.bizu.querycenter.config;

import com.bizu.querycenter.security.JwtAccessDeniedHandler;
import com.bizu.querycenter.security.JwtAuthenticationEntryPoint;
import com.bizu.querycenter.security.JwtFilter;
import com.bizu.querycenter.service.EmployeeDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final EmployeeDetailsServiceImpl employeeDetailsService;

    public SecurityConfig(JwtFilter jwtFilter,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          EmployeeDetailsServiceImpl employeeDetailsService) {
        this.jwtFilter = jwtFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.employeeDetailsService = employeeDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/v1/employee").hasAuthority("ADMIN")
                        .requestMatchers("/v1/team").hasAuthority("USER")
                        .anyRequest().authenticated()
                        .and())
                .formLogin()
                .loginPage("/v1/auth/login").defaultSuccessUrl("/v1", true)
                .and()
                .logout()
                .logoutUrl("/v1/logout").logoutSuccessUrl("/v1/logout")
                .and()
                .httpBasic().disable()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring()
                .requestMatchers("/v1/auth/login", "/swagger-ui/*", "/v3/*"));
    }

    @Bean
    public WebMvcConfigurer configurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }
}
