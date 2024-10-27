package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService

    SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Desactiva la protección CSRF
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/**").permitAll() // Permite el acceso a todas las rutas
                    .anyRequest().permitAll()
            }
            .formLogin().disable() // Desactiva el formulario de inicio de sesión
            .logout().disable() // Desactiva el cierre de sesión
            .userDetailsService(customUserDetailsService)
        return http.build()
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }
}
