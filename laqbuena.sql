-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
-- Host: localhost    Database: crud_groovy
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apaterno` varchar(45) NOT NULL,
  `amaterno` varchar(45) NOT NULL,
  `cumple` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` VALUES 
(6, 'NuevoNombre', 'NuevoApaterno', 'NuevoAmaterno', '2002-04-02', 'ezurita0562', '$2a$10$CRkgFr5XrRJqhGEQiwXZGet7FktozcsJFxRKskQQZ/.M1Fd/szpxC', 1),
(12, 'Robincito80', 'Quintana', 'Romero', '2002-04-02', 'complex', '$2a$10$ASJhsxITax3kM2w9KR.3iOZyxMFfc.NoOIkMR.NVhqHCxLoSx0MAe', 1),
(13, 'robin', 'hood', 'zurita', '2001-12-30', 'complexAdmin', '$2a$10$1cvY4qJZlGj5Gfh.TB857ePR.OoLbiLj2NbtllJLICATheZIdPfcu', 1),
(19, 'BrayAN', 'Almogabar', 'Nolasco', '2001-05-21', 'BrayAN', '$2a$10$.wwD0GkUIWCGapKOhPVw1uPkitv4spNZDXidT3XGpf8H525DKYQ2C', 1),
(24, 'Roberto', 'Quintana', 'Romero', '2002-04-05', 'Complexrobi12', '$2a$10$tQJU1OnKUI/bvVbrINgZROrWI/DqT0I2DLaL1w/JEwfOY5Ws3KU3C', 1);

--
-- Table structure for table `usuarios_roles`
--

DROP TABLE IF EXISTS `usuarios_roles`;
CREATE TABLE `usuarios_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `rol_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_idx` (`usuario_id`),
  KEY `rol_idx` (`rol_id`),
  CONSTRAINT `rol` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuarios_roles`
--

INSERT INTO `usuarios_roles` VALUES 
(5, 13, 1), 
(11, 6, 2), 
(28, 12, 2);

--
-- Create admin user and grant privileges
--

-- Crear usuario 'admin' con contraseña
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';

-- Otorgar todos los privilegios sobre la base de datos 'crud_groovy'
GRANT ALL PRIVILEGES ON crud_groovy.* TO 'admin'@'localhost';

-- Aplicar los cambios de privilegios
FLUSH PRIVILEGES;

--
-- Finalización del script
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;



