package com.example.main.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsersConfig usersConfig;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(usersConfig.getAdminName()).password(passwordEncoder().encode(usersConfig.getAdminPassword())).authorities(ApplicationRoles.USER, ApplicationRoles.ADMIN).
                and().withUser(usersConfig.getUserName()).password(passwordEncoder().encode(usersConfig.getUserPassword())).authorities(ApplicationRoles.USER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().
                and().authorizeRequests().anyRequest().authenticated().
                and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().csrf().disable().headers().httpStrictTransportSecurity().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}