package com.example.aplicacionesmovilesnativasgroovy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/resources/**", "/", "/js/**", "/css/**").permitAll()  // Permitir acceso público a /register y recursos estáticos
                .anyRequest().authenticated()  // El resto de las rutas deben estar autenticadas
                .and()
            .formLogin()
                .loginPage("/register")  // Página de login personalizada
                .permitAll()
                .and()
            .logout()
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
