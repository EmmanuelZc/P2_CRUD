-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: crud_groovy
-- ------------------------------------------------------
-- Server version	8.0.39

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (5,'asdasdsa','asdasdas','asdasdsa','2002-04-02','ezurita056 ','12345',1),(6,'asdasdsa','asdasdas','asdasdsa','2002-04-02','ezurita0562','12345',1),(7,'asdasdsa','asdasdas','asdasdsa','2002-04-02','ezurita0562','$2a$10$UVU1aNBXgctXyWSDNPN54.Pac4MzqmCSwY23gcj/XtQYSf3EtqdnS',1),(8,'aaaaa','asdasdasadasdas','asdasdsa','2002-04-02','emmanuel','$2a$10$dfqtEmXyImmnynonCtw./.g4HKChPm5pOJHls3J4i/0aET4L1bSfS',1),(10,'Emmanuel','asdasdasadasdas','asdasdsas','2002-04-02','emmanuel','$2a$10$/Ywxd8b5eoSMRU7ZZy7Yve8tkDyIQDFJyFnukGcAyraX2z0iD/EYW',1),(11,'aaaaa','Z','asdasdsas','2002-04-02','siuu','$2a$10$blr0NYsZeY8ZZFBT3DCRPehofuUGQ2O0VDfEdo7ISREgxkfdx61Rm',1),(12,'Victor','Alcaraz','Cuevas','2000-10-30','victor','$2a$10$O1ue7hH8iMK91jyzscTFnejYEFSvB5mTvmmLknHKZGF87MVF1VYFe',1),(14,'GG','SS','WW','2000-03-01','usuarioprueba','$2a$10$q.Oh5xDRlBDILKjsYB/jDenM3Z/EUJOZb39C6NMaTm.AjeyPaar4K',1),(15,'qq','q','q','2001-04-03','prueba11','$2a$10$qBy3f1j6Igh0aqJ39.m8zOr0l7NJB8.KRAZ4xDRrp4eDnWQ7LD3lq',1),(16,'Jake','El','Perro','2002-02-02','usuarioprueba123','$2a$10$gXqcDwvTliRD2IcjgEY.iOrO4XklUI6kTwx2b6cl.vLYlnLaV3Rqu',1),(17,'VIKTOR','sharon','zurita','2000-01-01','Robin','',1),(18,'webos','al','zurita','2024-11-29','zurita','$2a$10$p6P2FfnCxjZzYUzRwRc6lujx3kTDA0BmSxpjyr/BFzUw64vA0zKhO',1),(19,'Chalino','sanchez','aaa','1943-09-08','chalino','',1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_roles`
--

DROP TABLE IF EXISTS `usuarios_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `rol_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_idx` (`usuario_id`),
  KEY `rol_idx` (`rol_id`),
  CONSTRAINT `rol` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_roles`
--

LOCK TABLES `usuarios_roles` WRITE;
/*!40000 ALTER TABLE `usuarios_roles` DISABLE KEYS */;
INSERT INTO `usuarios_roles` VALUES (2,8,2),(3,11,2),(4,12,1),(6,16,1),(7,17,2),(8,17,2),(9,18,1),(10,19,2),(11,19,2);
/*!40000 ALTER TABLE `usuarios_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 17:11:50
