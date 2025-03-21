package com.springboot.dev_spring_boot_demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

//08mar2025
@Configuration
public class SecurityConfig {
    //15mar2025
    @Bean
    public UserDetailsService userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select * from users where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select * from authorities where username=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers("/default").hasRole("STUDENT")
                        //Co the them cau lenh duoi, do cau o tren da cho phep truy cap duoi dang role: STUDENT
                        /*.requestMatchers("/home").hasRole("STUDENT")*/
                        .requestMatchers("/admin").hasRole("MANAGER")
                        .requestMatchers("/system").hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(form ->
                form.loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
        ).logout(logout -> logout.permitAll()
        ).exceptionHandling(configurer ->
                configurer.accessDeniedPage("/error")
        );
        return http.build();
    }
}
