package com.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class InMemorySecurityDemo {

    @Bean
    public UserDetailsService userDetailsService
            (PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("ganesh")
            .password(passwordEncoder.encode("1234"))
            .roles("USER")
            .build();

        UserDetails u2 = User.withUsername("sai")
            .password(passwordEncoder.encode("abcd"))
            .roles("USER")
            .build();
        UserDetails u3 = User.withUsername("naveen")
                .password(passwordEncoder.encode("abc123"))
              
                .build();
    
        return new InMemoryUserDetailsManager(user, u2,u3);
      
    } 
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    
   }
}