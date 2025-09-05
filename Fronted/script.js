// script.js

// --- PARTE 1: LÓGICA DE LA INTERFAZ 3D (CÓDIGO ORIGINAL) ---
document.addEventListener('DOMContentLoaded', () => {
    const loginFormElement = document.querySelector('.login-form');
    const container = document.querySelector('.login-container');

    // Efecto 3D al mover el mouse
    container.addEventListener('mousemove', (e) => {
        const rect = container.getBoundingClientRect();
        const x = e.clientX - rect.left - rect.width / 2;
        const y = e.clientY - rect.top - rect.height / 2;
        const rotateY = (x / (rect.width / 2)) * 15;
        const rotateX = (y / (rect.height / 2)) * -15;
        loginFormElement.style.transform = `rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
    });

    // Resetear posición cuando el mouse sale
    container.addEventListener('mouseleave', () => {
        loginFormElement.style.transform = 'rotateX(0deg) rotateY(0deg)';
    });

    
    // --- PARTE 2: LÓGICA PARA CONECTAR CON EL BACKEND (NUEVO CÓDIGO) ---
    
    // Seleccionamos el formulario por su nuevo ID
    const loginForm = document.getElementById('loginForm');

    // Añadimos el "escuchador" para el evento 'submit'
    loginForm.addEventListener('submit', function(event) {
        // Prevenimos que la página se recargue
        event.preventDefault(); 
        
        // Llamamos a la función que hará la petición a la API
        handleLogin();
    });

    // Función asíncrona para manejar la lógica del login
    async function handleLogin() {
        // Seleccionamos los campos de entrada y el botón
        const emailInput = document.getElementById('email');
        const passwordInput = document.getElementById('password');
        const loginButton = document.getElementById('loginButton');

        // Deshabilitamos el botón para evitar múltiples envíos
        loginButton.disabled = true;
        loginButton.textContent = 'Verificando...';

        try {
            // Hacemos la petición a la API con los datos del formulario
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: emailInput.value,
                    password: passwordInput.value
                })
            });

            // Si la respuesta del servidor es exitosa (ej. código 200)
            if (response.ok) {
                const data = await response.json();
                
                // Guardamos el token recibido en el almacenamiento local del navegador
                localStorage.setItem('jwt_token', data.token);

                alert('¡Inicio de sesión exitoso! Serás redirigido.');
                
                // Redirigimos al usuario a la página principal (¡Crea este archivo!)
                window.location.href = 'principal.html'; 
            
            } else {
                // Si el servidor responde con un error (ej. 401 Unauthorized)
                alert('Error: Email o contraseña incorrectos.');
                loginButton.disabled = false; // Habilitamos el botón de nuevo
                loginButton.textContent = 'Entrar';
            }

        } catch (error) {
            // Si hay un error de red (ej. el backend no está corriendo)
            console.error('Error de conexión:', error);
            alert('Error de conexión: No se pudo conectar al servidor.');
            loginButton.disabled = false; // Habilitamos el botón de nuevo
            loginButton.textContent = 'Entrar';
        }
    }
});