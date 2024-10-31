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
        .csrf().ignoringRequestMatchers("/registro", "/login", "/register", "/api/**")
        .and()
        .authorizeHttpRequests()
            .requestMatchers("/error","/registro","/api/**", "/register", "/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/src/**", "/templates/**", "/").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")  
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error=true")  
            .permitAll()
            .and()
         .logout()
                .logoutUrl("/logout")   // URL para logout
                .logoutSuccessUrl("/login")  // Redirección después del logout
                .invalidateHttpSession(true)  // Invalida la sesión actual
                .deleteCookies("JSESSIONID")  // Borra la cookie de sesión
                .permitAll()
        .and()
        .userDetailsService(customUserDetailsService);
    
    return http.build();
}


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }
}
