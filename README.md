# NBL CINEMAX - Sistema de Gestión de Cines 

Este repositorio contiene el código fuente para el proyecto NBL CINEMAX, un sistema completo para la administración de cines. El proyecto incluye un backend robusto construido con **Spring Boot** para gestionar la lógica de negocio y una API RESTful, así como un frontend dinámico para la interacción del usuario.

##  Introducción

NBL CINEMAX nace de la necesidad de modernizar los sistemas de gestión de cines tradicionales. El objetivo es reemplazar los procesos manuales, las largas filas y la gestión ineficiente de salas por una plataforma digital centralizada que mejore tanto la experiencia del cliente como la eficiencia operativa del personal administrativo.

##  Características Principales

El proyecto está dividido en dos componentes principales: un backend que expone una API REST y un frontend que consume esta API.

### Backend (Spring Boot)
- **API RESTful:** Endpoints bien definidos para todas las operaciones.
- **Gestión (CRUD):** Módulos completos para administrar **Películas** y **Ciudades**.
- **Seguridad Robusta:** Autenticación de usuarios basada en **JSON Web Tokens (JWT)** y autorización con **Spring Security**.
- **Persistencia de Datos:** Integración con bases de datos relacionales (MySQL) a través de **Spring Data JPA**.

### Frontend (HTML, CSS, JavaScript)
- **Login de Usuario:** Interfaz de inicio de sesión moderna y segura que se comunica con el backend.
- **Panel de Administración (Dashboard):**
    - Interfaz amigable para gestionar el contenido del cine.
    - Carga dinámica de datos sin recargar la página.
    - CRUD visual para Ciudades (en tabla) y Películas (en tarjetas con póster).
    - Notificaciones en tiempo real para confirmar acciones del usuario.

##  Tecnologías Utilizadas

- **Backend:**
  - Java 17+
  - Spring Boot 3
  - Spring Security (JWT)
  - Spring Data JPA (Hibernate)
  - Maven
  - MySQL

- **Frontend:**
  - HTML5
  - CSS3 (Diseño moderno con Flexbox y Grid)
  - JavaScript (Vanilla JS, Fetch API, Async/Await)

- **Herramientas de Desarrollo:**
  - Git & GitHub
  - Spring Tool Suite 4 (o cualquier IDE compatible con Spring)
  - Postman (para pruebas de API)
  - MySQL Workbench
