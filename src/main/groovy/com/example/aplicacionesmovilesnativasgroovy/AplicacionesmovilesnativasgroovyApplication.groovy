package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Clase principal de la aplicación Spring Boot para Aplicaciones Móviles Nativas con Groovy.
 * Configura la aplicación y habilita el soporte para JPA Repositories.
 */
@SpringBootApplication
@EnableJpaRepositories  // Habilita el soporte para JPA Repositories
class AplicacionesmovilesnativasgroovyApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos (opcional).
     */
    static void main(String[] args) {
        SpringApplication.run(AplicacionesmovilesnativasgroovyApplication, args)
    }

}
