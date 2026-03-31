-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: biblioteca
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `autores`
--

DROP TABLE IF EXISTS `autores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autores` (
  `idAutor` bigint NOT NULL AUTO_INCREMENT,
  `nombreAutor` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `nacionalidadAutor` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `fechaNacimiento` date NOT NULL,
  `imagenAutor` varchar(256) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idAutor`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autores`
--

LOCK TABLES `autores` WRITE;
/*!40000 ALTER TABLE `autores` DISABLE KEYS */;
INSERT INTO `autores` VALUES (1,'Gabriel García Marques','Colombia','1927-03-06','Gabriel_Garcia_Marquez.jpg'),(2,'J.K. Rowling','Reino Unido','1965-07-31','JK_Rowling.png'),(3,'Antoine de Saint-Exupéry','Francia','1900-06-29','Antoine_de_Saint_Exupery.jpg'),(4,'George Orwell','Reino Unido','1903-06-25','George_Orwell.jpg'),(5,'Dan Brown','Estados Unidos','1964-06-22','Dan_Brown.jpg'),(6,'Jane Austen','Inglaterra','1775-12-16','Jane_Austen.jpg'),(7,'Suzanne Collins','Estados Unidos','1962-08-10','Suzanne_Collins.jpg'),(8,'Carlos Ruiz Zafón','España','1964-09-25','Carlos_Ruiz_Zafon.jpeg'),(9,'Makoto Yukimura','Japón','1976-05-08','Makoto_Yukimura.jpg'),(10,'Alan Moore','Reino Unido','1953-11-18','Alan_Moore.jpg'),(11,'Grant Morrison','Estados Unidos','1960-01-31','grant-morrison.webp'),(12,'George R. R. Martin','Estados Unidos','1948-09-20','George_Martin.webp');
/*!40000 ALTER TABLE `autores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `idCategoria` bigint NOT NULL AUTO_INCREMENT,
  `nombreCategoria` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `imagenCategoria` varchar(256) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Literatura','literatura.jpg'),(2,'Fantasía','fantasia.jpg'),(3,'Política','politica.jpg'),(4,'Misterio','misterio.jpg'),(5,'Romance','romance.jpg'),(6,'Manga','manga.jpg'),(7,'Cómic','comic.jpg'),(8,'Ciencia Ficción','sciencefiction.jpg'),(9,'Terror','terror.jpg'),(10,'Drama','drama.jpg'),(11,'Manwha','Manwha2.webp'),(12,'Comedia','speedy_gonzales.avif'),(13,'Novela','novela.webp');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clasificaciones`
--

DROP TABLE IF EXISTS `clasificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clasificaciones` (
  `idLibroCatFK` bigint NOT NULL,
  `idCategoriaFK` bigint NOT NULL,
  PRIMARY KEY (`idLibroCatFK`,`idCategoriaFK`),
  KEY `idCategoriaFK` (`idCategoriaFK`),
  CONSTRAINT `clasificaciones_ibfk_1` FOREIGN KEY (`idLibroCatFK`) REFERENCES `libros` (`idLibro`),
  CONSTRAINT `clasificaciones_ibfk_2` FOREIGN KEY (`idCategoriaFK`) REFERENCES `categorias` (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clasificaciones`
--

LOCK TABLES `clasificaciones` WRITE;
/*!40000 ALTER TABLE `clasificaciones` DISABLE KEYS */;
INSERT INTO `clasificaciones` VALUES (1,1),(3,1),(2,2),(7,2),(13,2),(4,3),(11,3),(5,4),(8,4),(10,4),(6,5),(9,6),(12,6),(10,7),(11,7),(2,8),(7,8),(10,8),(13,8),(10,10),(11,10),(13,10);
/*!40000 ALTER TABLE `clasificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libros`
--

DROP TABLE IF EXISTS `libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libros` (
  `idLibro` bigint NOT NULL AUTO_INCREMENT,
  `tituloLibro` varchar(256) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `anioPublicacion` int DEFAULT NULL,
  `sinopsisLibro` longtext COLLATE utf8mb4_spanish_ci,
  `estadoLibro` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `portadaLibro` varchar(256) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `idAutorFK` bigint NOT NULL,
  PRIMARY KEY (`idLibro`),
  UNIQUE KEY `tituloLibro` (`tituloLibro`),
  KEY `fk_libros_autores` (`idAutorFK`),
  CONSTRAINT `fk_libros_autores` FOREIGN KEY (`idAutorFK`) REFERENCES `autores` (`idAutor`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (1,'Cien años de soledad',1967,'Entre la boda de José Arcadio Buendía con Amelia Iguarán hasta la maldición de Aureliano Babilonia transcurre todo un siglo. Cien años de soledad para una estirpe única, fantástica, capaz de fundar una ciudad tan especial como Macondo y de engendrar niños con cola de cerdo. En medio, una larga docena de personajes dejarán su impronta a las generaciones venideras, que tendrán que lidiar con un mundo tan complejo como sencillo.','Disponible','cienaniosdesoledad-portada.jpg',1),(2,'Harry Potter y la Piedra Filosofal',1997,'Harry Potter se ha quedado huérfano y vive en casa de sus abominables tíos y del insoportable primo Dudley. Harry se siente muy triste y solo, hasta que un buen día recibe una carta que cambiará su vida para siempre. En ella le comunican que ha sido aceptado como alumno en el colegio interno Hogwarts de magia y hechicería. A partir de ese momento, la suerte de Harry da un vuelco espectacular. En esa escuela tan especial aprenderá encantamientos, trucos fabulosos y tácticas de defensa contra las malas artes. ','Disponible','harrypotterylapiedrafilosofal-portada.jpg',2),(3,'El Principito',1943,'El Principito narra la historia de un niño príncipe que vive en un pequeño asteroide y que cae a la Tierra, donde conoce a un piloto varado en el desierto. Ambos entablan una conversación en clave poética donde hablan de filosofía, de crítica social, del amor, del honor y de mucho de lo que nos hace humanos. ','Disponible','elprincipito.jpeg',3),(4,'1984',1949,'Londres, 1984: el Gran Hermano controla hasta el último detalle de la vida privada de los ciudadanos. Winston Smith trabaja en el Ministerio de la Verdad reescribiendo y retocando la historia para un estado totalitario que somete de forma despiadada a la población, hasta que siente que no quiere contribuir más a este sistema perverso y decide rebelarse.','Disponible','1984-portada.jpg',4),(5,'El Código Da Vinci',2003,'Robert Langdon recibe una llamada en mitad de la noche: el conservador del museo del Louvre ha sido asesinado en extrañas circunstancias y junto a su cadáver ha aparecido un desconcertante mensaje cifrado. Al profundizar en la investigación, Langdon, experto en simbología, descubre que las pistas conducen a las obras de Leonardo Da Vinci… y que están a la vista de todos, ocultas por el ingenio del pintor.','Disponible','elcodigodavinci-portada.jpg',5),(6,'Orgullo y Prejuicio',1913,'Con la llegada del rico y apuesto señor Darcy a su región, las vidas de los Bennet y sus cinco hijas se vuelven del revés. El orgullo y la distancia social, la astucia y la hipocresía, los malentendidos y los juicios apresurados abocan a los personajes al escándalo y al dolor, pero también a la comprensión, el conocimiento y el amor verdadero.','Disponible','orgulloyprejuicio-portada.jpg',6),(7,'Los Juegos del Hambre',2008,'Es la hora. Ya no hay vuelta atrás. Los juegos van a comenzar. Los tributos deben salir a la Arena y luchar por sobrevivir. Ganar significa Fama y riqueza, perder significa la muerte segura... ¡Que empiecen los Septuagésimo Cuartos Juegos del Hambre! Un pasado de guerras ha dejado los 12 distritos que dividen Panem bajo el poder tiránico del Capitolio. Sin libertad y en la pobreza, nadie puede salir de los límites de su distrito. Sólo una chica de 16 años, Katniss Everdeen, osa desafiar las normas para conseguir comida.','Disponible','losjuegosdelhambre-portada.jpg',7),(8,'La Sombra del Viento',2001,'Un amanecer de 1945, un muchacho es conducido por su padre a un misterioso lugar oculto en el corazón de la ciudad vieja: El Cementerio de los Libros Olvidados. Allí, Daniel Sempere encuentra un libro maldito que cambia el rumbo de su vida y le arrastra a un laberinto de intrigas y secretos enterrados en el alma oscura de la ciudad. ','Disponible','lasombradelviento-portada.jpg',8),(9,'Vinland Saga',2005,'Desafiando las rígidas leyes vikingas y a pesar de ser un gran guerrero, Thors decide huir de la cruenta vida que llevaba con su familia. Al ser descubierto, será perseguido durante su viaje marítimo por un mercenario de nombre Askeladd, cayendo finalmente en una emboscada junto a su expedición. Ganará la batalla contra sus atacantes, aunque a un alto precio: Thors dará su vida para que el resto de la tripulación, incluido su hijo Thorfinn, vivan. Desde aquel instante Thorfinn jura vengarse. Sin embargo, será apresado por Askeladd y obligado a enrolarse en su barco. Pero aún le quedará una esperanza. Según el código vikingo, Thorfinn tendrá derecho de retar a Askeladd a duelo si cumple una serie de difíciles tareas, como sabotear o matar a generales enemigos, lo que le llevará a involucrarse incluso en la guerra por la corona de Inglaterra.','Disponible','vinlandsaga-portada.jpg',9),(10,'Watchmen',1986,'El asesinato de un antiguo superhéroe y la posterior investigación del crimen no son más que el comienzo de uno de los cómics más aclamados de la historia, uno donde el mundo se aproxima a una catástrofe nuclear mientras sus defensores se ven obligados a resurgir de sus cenizas.','Disponible','watchmen-portada.png',10),(11,'V de Vendetta',1982,'Estamos en el año 1997. Ha finalizado la III Guerra Mundial. Inglaterra se ha convertido en un estado fascista. En estos tiempos de desesperación y oscuridad, aparece un misterioso individuo que se hace llamar \"V\" y que ataca a los intereses y agentes del estado, creando el caos. ','Disponible','vdevendetta.jpg',10),(12,'Vinland Saga 2',2005,'Segundo Volumen de Vinland Saga','Disponible','vinlandsaga2-portada.jpg',9),(13,'Juego de Tronos',1996,'Primer libro del autor que comenzaría todo un universo con distintas precuelas y spin off.','Disponible','Juego_de_Tronos.webp',12);
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos`
--

DROP TABLE IF EXISTS `prestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamos` (
  `idPrestamo` bigint NOT NULL AUTO_INCREMENT,
  `fechaInicio` date NOT NULL,
  `fechaDevolucionEsperada` date NOT NULL,
  `fechaDevolucionReal` date DEFAULT NULL,
  `estado` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `idUsuarioFK` bigint NOT NULL,
  PRIMARY KEY (`idPrestamo`),
  KEY `fk_prestamos_usuarios` (`idUsuarioFK`),
  CONSTRAINT `fk_prestamos_usuarios` FOREIGN KEY (`idUsuarioFK`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos`
--

LOCK TABLES `prestamos` WRITE;
/*!40000 ALTER TABLE `prestamos` DISABLE KEYS */;
INSERT INTO `prestamos` VALUES (1,'2025-11-21','2025-11-28','2025-11-28','Devuelto',1),(2,'2025-11-19','2025-11-26','2026-01-22','Devuelto',1),(3,'2026-01-22','2026-01-28','2026-01-22','Devuelto',1),(4,'2026-01-22','2026-01-28','2026-01-22','Devuelto',1),(5,'2026-01-22','2026-01-28','2026-01-22','Devuelto',2),(6,'2026-01-20','2026-01-28',NULL,'Pendiente',1),(7,'2026-01-20','2026-01-28',NULL,'Pendiente',1),(8,'2026-01-22','2026-01-28',NULL,'Pendiente',1);
/*!40000 ALTER TABLE `prestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamos_libros`
--

DROP TABLE IF EXISTS `prestamos_libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamos_libros` (
  `idPrestamoFK` bigint NOT NULL,
  `idLibroPrestFK` bigint NOT NULL,
  PRIMARY KEY (`idPrestamoFK`,`idLibroPrestFK`),
  KEY `idLibroPrestFK` (`idLibroPrestFK`),
  CONSTRAINT `prestamos_libros_ibfk_1` FOREIGN KEY (`idPrestamoFK`) REFERENCES `prestamos` (`idPrestamo`),
  CONSTRAINT `prestamos_libros_ibfk_2` FOREIGN KEY (`idLibroPrestFK`) REFERENCES `libros` (`idLibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamos_libros`
--

LOCK TABLES `prestamos_libros` WRITE;
/*!40000 ALTER TABLE `prestamos_libros` DISABLE KEYS */;
INSERT INTO `prestamos_libros` VALUES (2,2),(4,3),(3,6),(2,7),(8,7),(1,9),(1,10),(5,10),(6,11),(7,12);
/*!40000 ALTER TABLE `prestamos_libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puntuaciones`
--

DROP TABLE IF EXISTS `puntuaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puntuaciones` (
  `idUsuarioPuntFK` bigint NOT NULL,
  `idLibroPuntFK` bigint NOT NULL,
  `puntuacion` double NOT NULL,
  PRIMARY KEY (`idUsuarioPuntFK`,`idLibroPuntFK`),
  KEY `idLibroPuntFK` (`idLibroPuntFK`),
  CONSTRAINT `puntuaciones_ibfk_1` FOREIGN KEY (`idUsuarioPuntFK`) REFERENCES `usuarios` (`idUsuario`),
  CONSTRAINT `puntuaciones_ibfk_2` FOREIGN KEY (`idLibroPuntFK`) REFERENCES `libros` (`idLibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntuaciones`
--

LOCK TABLES `puntuaciones` WRITE;
/*!40000 ALTER TABLE `puntuaciones` DISABLE KEYS */;
INSERT INTO `puntuaciones` VALUES (1,7,4),(1,9,5);
/*!40000 ALTER TABLE `puntuaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `idRol` bigint NOT NULL AUTO_INCREMENT,
  `nombreRol` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`idRol`),
  UNIQUE KEY `nombreRol` (`nombreRol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ADMIN'),(3,'EMPLOYEE'),(1,'USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_usuarios`
--

DROP TABLE IF EXISTS `roles_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_usuarios` (
  `idUsuarioRolFK` bigint NOT NULL,
  `idRolUsuarioFK` bigint NOT NULL,
  PRIMARY KEY (`idUsuarioRolFK`,`idRolUsuarioFK`),
  KEY `idRolUsuarioFK` (`idRolUsuarioFK`),
  CONSTRAINT `roles_usuarios_ibfk_1` FOREIGN KEY (`idUsuarioRolFK`) REFERENCES `usuarios` (`idUsuario`),
  CONSTRAINT `roles_usuarios_ibfk_2` FOREIGN KEY (`idRolUsuarioFK`) REFERENCES `roles` (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_usuarios`
--

LOCK TABLES `roles_usuarios` WRITE;
/*!40000 ALTER TABLE `roles_usuarios` DISABLE KEYS */;
INSERT INTO `roles_usuarios` VALUES (2,1),(4,1),(1,2),(3,3);
/*!40000 ALTER TABLE `roles_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idUsuario` bigint NOT NULL AUTO_INCREMENT,
  `nombreRealUsuario` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `apellidosUsuario` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `direccionUsuario` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `ciudadUsuario` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `localidadUsuario` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `cpUsuario` int DEFAULT NULL,
  `telefonoUsuario` int DEFAULT NULL,
  `emailUsuario` varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `claveUsuario` varchar(256) COLLATE utf8mb4_spanish_ci NOT NULL,
  `iconoUsuario` varchar(256) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `emailUsuario` (`emailUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Denis','Peña','Kansas City 82, 2A','Sevilla','Sevilla',41007,689253654,'denis@gmail.com','$2y$10$8.Ot30.5.uHAtes.jBdqsuz5y7CLnGvJZNZEZhSp.XX5uZMPGMxC.','denis-logo2.png'),(2,'Samuel','Soto','C/Sol 20','Sevilla','Sevilla',41020,614852369,'samu_soto@gmail.com','$2a$10$fgJhdH4ko86tdZZa8xLeL.isgYOIOikDrSydlni58qgbtTZOaiJcG','davinci.png'),(3,'Carlos','García Pimienta','C/Estrella 10','Sevilla','Camas',41001,654123987,'carlos@gmail.com','$2a$10$W6EBGmAbcXwqxfSnTSMJQO20.ATHFBoxiAz./MAkfLuZqXzTj5OJ.',NULL),(4,'Roberto','Carlos',NULL,NULL,NULL,0,0,'robeCarlos@gmail.com','$2a$10$UkYZ05fj90UVfeLwXWxfK.j8ge6cRSVZm/4bI5tEHbU8AFcez1ozW',NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-16 22:31:51
