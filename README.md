# NBL CINEMAX - Sistema de Gestión de Cines (Spring Boot + JS)
Este repositorio contiene el código fuente del sistema de gestión de cines NBL CINEMAX. El proyecto implementa un backend robusto con **Spring Boot** que expone una API RESTful y un frontend dinámico construido con **HTML, CSS y JavaScript nativo** para la administración de contenido.

##  Autores
Neider Alirio Piza Basto
Leider Joanny Esteban Lozano
Brayan Yecid Aparicio Goyeneche

##  Descripción del Proyecto

NBL CINEMAX fue concebido para modernizar la administración de un cine, reemplazando los procesos manuales por una plataforma digital centralizada. El sistema se enfoca en dos áreas clave: la autenticación segura de administradores y la gestión de la información fundamental del cine (ciudades y cartelera de películas).

El objetivo principal es proporcionar una herramienta interna eficiente y fácil de usar para el personal administrativo, sentando las bases para futuras expansiones como la venta de boletos en línea.

##  Características Implementadas

-   **Backend con Arquitectura REST:**
    -   API RESTful con endpoints claros y definidos para cada recurso.
    -   Seguridad basada en **JSON Web Tokens (JWT)** para proteger las rutas de administración.
    -   Persistencia de datos en una base de datos relacional **MySQL**, gestionada a través de **Spring Data JPA**.

-   **Frontend Interactivo:**
    -   **Interfaz de Login:** Un formulario de inicio de sesión visualmente atractivo y funcional que se comunica con el backend para autenticar a los usuarios.
    -   **Panel de Administración (Dashboard):** Una interfaz de usuario de panel único donde el contenido se carga dinámicamente sin necesidad de recargar la página.
    -   **Gestión de Ciudades:** Módulo CRUD (Crear, Leer, Actualizar, Eliminar) completo para administrar las ciudades donde el cine tiene presencia, presentado en una tabla clara.
    -   **Gestión de Películas:** Módulo CRUD visual para la cartelera, presentando las películas en un formato de tarjetas con sus pósteres para una identificación rápida.
    -   **Experiencia de Usuario Mejorada:** Uso de ventanas modales para los formularios de creación/edición y notificaciones "toast" para confirmar acciones, proporcionando un flujo de trabajo fluido.

##  Tecnologías Utilizadas

| Área                | Tecnologías                                                              |
| ------------------- | ------------------------------------------------------------------------ |
| **Backend**         | `Java 17+`, `Spring Boot`, `Spring Security (JWT)`, `Spring Data JPA (Hibernate)`, `Maven` |
| **Base de Datos**   | `MySQL 8.0`                                                              |
| **Frontend**        | `HTML5`, `CSS3 (Flexbox, Grid)`, `JavaScript (ES6+, Fetch API, Async/Await)` |
| **Herramientas**    | `Git`, `GitHub`, `Spring Tool Suite 4`, `Postman`, `MySQL Workbench`       |

##  Guía de Instalación y Puesta en Marcha

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno de desarrollo local.

### Prerrequisitos
-   Java JDK 17 o superior.
-   Apache Maven 3.6+.
-   Servidor de MySQL 8.0 y una herramienta de gestión como MySQL Workbench.
-   Un IDE para Java/Spring, como Spring Tool Suite 4 (STS4) o IntelliJ IDEA.
-   Git instalado.

### 1. Clonar el Repositorio
Abre una terminal y clona el proyecto desde GitHub:
```bash
git clone [https://github.com/tu-usuario/nombre-del-repositorio.git](https://github.com/NeyderPiza/ProyectoEntonos-NBL-Cinemax)
cd nombre-del-repositorio

### 2. Configuración de la Base de Datos
1.  **Crea la Base de Datos:** Abre MySQL Workbench y ejecuta la siguiente consulta para crear la base de datos:
    ```sql
    CREATE DATABASE nbl_cinemax_db;
    ```
2.  **Crea las Tablas:** Dentro de la base de datos recién creada, ejecuta el script SQL proporcionado en el repositorio para generar todas las tablas y sus relaciones.

### 3. Configuración del Backend (Spring Boot)
1.  **Importa el Proyecto:** Abre tu IDE (STS4) e importa el proyecto como un "Existing Maven Project". La carpeta a importar es la que contiene el archivo `pom.xml`.
2.  **Configura la Conexión:** Navega a `src/main/resources/` y abre el archivo `application.properties`. Modifica las siguientes líneas con tus credenciales de MySQL:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3066/nbl_cinemax_db
    spring.datasource.username=tu_usuario_mysql
    spring.datasource.password=tu_contraseña_mysql
    ```
3.  **Ejecuta el Backend:** Localiza la clase principal (ej. `NblCinemaxApiApplication.java`) y ejecútala como una "Spring Boot App". El servidor se iniciará en `http://localhost:8080`.

### 4. Ejecución del Frontend (HTML/CSS/JS)
1.  Navega a la carpeta del frontend que contiene los archivos `index.html`, `principal.html`, etc.
2.  La forma más sencilla de ejecutarlo es abrir el archivo `index.html` directamente en tu navegador web (Google Chrome, Firefox, etc.).
3.  **(Recomendado)** Para una mejor experiencia de desarrollo, puedes usar una extensión como **"Live Server"** en Visual Studio Code. Simplemente haz clic derecho en `index.html` y selecciona "Open with Live Server".

¡Y listo! Ya puedes navegar a la página de login, autenticarte y empezar a usar el panel de administración.

##  Documentación de la API

Los siguientes son los endpoints principales implementados en el backend.

---
**Autenticación**
| Método | Endpoint                    | Descripción                               | Requiere Auth |
|--------|-----------------------------|-------------------------------------------|---------------|
| `POST` | `/api/auth/register`        | Registra un nuevo usuario administrador.  | No            |
| `POST` | `/api/auth/login`           | Autentica a un usuario y devuelve un token JWT. | No            |

---
**Ciudades**
| Método | Endpoint                    | Descripción                               | Requiere Auth |
|--------|-----------------------------|-------------------------------------------|---------------|
| `GET`  | `/api/ciudades`             | Obtiene la lista de todas las ciudades.   | No            |
| `POST` | `/api/ciudades`             | Crea una nueva ciudad.                    | **Sí**        |
| `PUT`  | `/api/ciudades/{id}`        | Actualiza una ciudad existente.           | **Sí**        |
| `DELETE`| `/api/ciudades/{id}`        | Elimina una ciudad.                       | **Sí**        |

---
**Películas**
| Método | Endpoint                    | Descripción                               | Requiere Auth |
|--------|-----------------------------|-------------------------------------------|---------------|
| `GET`  | `/api/peliculas`            | Obtiene la lista de todas las películas.  | No            |
| `POST` | `/api/peliculas`            | Crea una nueva película.                  | **Sí**        |
| `PUT`  | `/api/peliculas/{id}`       | Actualiza una película existente.         | **Sí**        |
| `DELETE`| `/api/peliculas/{id}`       | Elimina una película.                     | **Sí**        |

*Para acceder a los endpoints protegidos, se debe incluir el token en la cabecera de la petición: `Authorization: Bearer <token_jwt>`.*
