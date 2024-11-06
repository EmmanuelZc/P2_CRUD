package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * Servicio personalizado de autenticación de usuarios para Spring Security.
 * Implementa la interfaz {@link UserDetailsService} para cargar los detalles del usuario
 * desde la base de datos utilizando el repositorio {@link UserRepository}.
 */
@Service
class CustomUserDetailsService implements UserDetailsService {

    /** Repositorio de usuarios para acceder a los datos de los usuarios en la base de datos */
    private final UserRepository userRepository

    /**
     * Constructor que inyecta el repositorio de usuarios.
     *
     * @param userRepository el repositorio de usuarios
     */
    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    /**
     * Carga un usuario por su nombre de usuario y genera los detalles necesarios para la autenticación.
     * Si el usuario no existe, lanza una excepción {@link UsernameNotFoundException}.
     *
     * @param username el nombre de usuario del usuario a autenticar
     * @return un objeto {@link UserDetails} con la información de autenticación del usuario
     * @throws UsernameNotFoundException si el usuario no es encontrado
     */
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado")
        }

        // Convertir los roles del usuario en una lista de autoridades para Spring Security
        def authorities = user.roles.collect { 
            new SimpleGrantedAuthority(it.nombre) 
        }

        return new org.springframework.security.core.userdetails.User(
                user.username,
                user.password,
                user.enabled,
                true, true, true,
                authorities
        )
    }
}
