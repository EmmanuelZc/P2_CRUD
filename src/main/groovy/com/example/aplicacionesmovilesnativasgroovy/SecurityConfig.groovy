package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import com.example.utils.DatabaseUtils

/**
 * Clase de configuración de seguridad que define las reglas de seguridad para la aplicación.
 * Configura el servicio de usuario, el cifrado de contraseñas y las rutas protegidas.
 */
@Configuration
class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService

    /**
     * Constructor que inyecta el servicio de detalles de usuario personalizado.
     *
     * @param customUserDetailsService Servicio que carga los detalles del usuario.
     */
    SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService
    }
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
    .csrf().ignoringRequestMatchers("/registro", "/login", "/register", "/api/**")
    .and()
    .authorizeHttpRequests()
        .requestMatchers("/error", "/registro", "/api/**", "/register", "/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/src/**", "/templates/**").permitAll()
        .anyRequest().authenticated()
        .and()
    .formLogin()
        .loginPage("/login")  
        .defaultSuccessUrl("/", true)
        .failureUrl("/login?error=true")  
        .permitAll()
        .and()
    .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .permitAll()
    .and()
    .userDetailsService(customUserDetailsService); // Verifica que esté usando el CustomUserDetailsService

    return http.build();
}

    /**
     * Proporciona un bean de codificador de contraseñas que utiliza BCrypt.
     *
     * @return una instancia de BCryptPasswordEncoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

    /**
     * Proporciona un bean de utilidad de base de datos.
     *
     * @return una instancia de DatabaseUtils.
     */
    @Bean
    DatabaseUtils databaseUtils() {
        return new DatabaseUtils()
    }
}
