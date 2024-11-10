package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.security.core.authority.SimpleGrantedAuthority
@Service
class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameWithRoles(username);
        if (user == null) {
            System.out.println("Usuario no encontrado: " + username)  // <-- Agrega esta línea
            throw new UsernameNotFoundException("Usuario no encontrado")
        }

        System.out.println("Usuario encontrado: " + username)  // <-- Agrega esta línea

           def authorities = user.userRoles.collect { 
            new SimpleGrantedAuthority(it.role.nombre) 
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
