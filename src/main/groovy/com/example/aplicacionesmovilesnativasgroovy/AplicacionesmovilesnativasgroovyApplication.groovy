package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories  // Habilita el soporte para JPA Repositories

class AplicacionesmovilesnativasgroovyApplication {

	static void main(String[] args) {
		SpringApplication.run(AplicacionesmovilesnativasgroovyApplication, args)
	}

}
