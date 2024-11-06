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

    /**
     * Configura la cadena de filtros de seguridad.
     * Define las reglas de acceso, el manejo de autenticación, y la configuración de logout.
     *
     * @param http Objeto HttpSecurity para personalizar las reglas de seguridad.
     * @return la cadena de filtros de seguridad configurada.
     * @throws Exception si hay errores en la configuración de seguridad.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().ignoringRequestMatchers("/registro", "/login", "/register", "/api/**") // Ignora CSRF en estas rutas
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/error", "/registro", "/api/**", "/register", "/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/src/**", "/templates/**", "/").permitAll() // Permite acceso a estas rutas sin autenticación
                .anyRequest().authenticated() // Requiere autenticación para otras rutas
                .and()
            .formLogin()
                .loginPage("/login")  // Página de inicio de sesión personalizada
                .defaultSuccessUrl("/", true) // Redirección después de inicio exitoso
                .failureUrl("/login?error=true") // Redirección en caso de error en inicio
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout") // URL de logout
                .logoutSuccessUrl("/login") // Redirección después de logout
                .invalidateHttpSession(true) // Invalida la sesión
                .deleteCookies("JSESSIONID") // Borra la cookie de sesión
                .permitAll()
            .and()
            .userDetailsService(customUserDetailsService) // Configura el servicio de detalles de usuario
        
        return http.build()
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
