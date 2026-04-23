# 📚 API de Biblioteca Digital

<p align="center">
  API REST desarrollada con <b>Spring Boot</b> para la gestión completa de una biblioteca digital.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.10-brightgreen" />
  <img src="https://img.shields.io/badge/MySQL-Database-blue" />
  <img src="https://img.shields.io/badge/Maven-Build-red" />
</p>

---

## 🚀 Descripción

Esta API permite gestionar una biblioteca digital donde los usuarios pueden:

- Consultar libros  
- Registrarse e iniciar sesión  
- Solicitar préstamos  
- Buscar por categorías, autores, etc.  
- Valorar libros mediante puntuaciones ⭐  

Está diseñada siguiendo buenas prácticas de desarrollo backend, arquitectura REST y seguridad.

---

## 🧩 Arquitectura

<p align="center">
  <i>Clientes → API REST → Base de Datos</i>
</p>

- 📱 Clientes: Web, móvil o aplicaciones externas  
- ⚙️ Backend: Spring Boot  
- 🗄️ Base de datos: MySQL  
- 🔐 Seguridad: JWT + roles  

---

## ✨ Funcionalidades principales

### 📖 Gestión de libros
- Crear, actualizar, eliminar y consultar libros  

### 👤 Gestión de usuarios
- Registro e inicio de sesión  
- Roles (admin, usuario)  

### 🔄 Préstamos
- Solicitar y devolver libros  
- Historial de préstamos  

### 🔍 Búsqueda y filtros
- Por título, autor, categoría, idioma, etc.  

### ⭐ Puntuaciones
- Los usuarios pueden valorar libros  

### 🔐 Seguridad
- Autenticación con JWT  
- Control de acceso basado en roles  

---

## 🛠️ Tecnologías utilizadas

- **Spring Boot**  
- **Spring Data JPA**  
- **Spring Security**  
- **MySQL**  
- **Maven**  

---

## 📂 Estructura del proyecto

```bash
src/
 ├── controllers/
 ├── services/
 ├── repositories/
 ├── models/
 ├── security/
 └── dto/
```

---

## ⚙️ Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/denispena7/API_BibliotecaDigital.git
cd tu-repo
```

### 2. Configurar la base de datos

Editar `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

### 3. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

---

## 🔑 Autenticación

La API utiliza **JWT**:

1. Login → obtiene token  
2. Incluir en headers:

```http
Authorization: Bearer TU_TOKEN
```

---

## 📌 Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET    | /libros  | Listar libros |
| POST   | /libros  | Crear libro |
| GET    | /usuarios | Listar usuarios |
| POST   | /auth/login | Login |
| POST   | /prestamos | Crear préstamo |
| POST   | /puntuaciones | Valorar libro |

---

## 🎯 Objetivo del proyecto

Este proyecto forma parte de mi portfolio como desarrollador backend, demostrando:

- Diseño de APIs REST  
- Seguridad con JWT  
- Arquitectura limpia  
- Persistencia con JPA  
- Buenas prácticas en Java  

---

## 📬 Contacto

- 💼 LinkedIn: [https://www.linkedin.com/in/penadenis/](#)  
- 💻 GitHub: [https://github.com/denispena7](#)  

---

<p align="center">
  ⭐ Si te gusta el proyecto, ¡dale una estrella!
</p>
