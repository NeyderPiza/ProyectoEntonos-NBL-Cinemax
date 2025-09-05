// admin.js

// Ejecutamos el código cuando todo el HTML ha sido cargado
document.addEventListener('DOMContentLoaded', () => {
    
    // --- 1. SEGURIDAD Y CONFIGURACIÓN INICIAL ---
    const token = localStorage.getItem('jwt_token');

    // Si no hay token, el usuario no ha iniciado sesión. Lo redirigimos.
    if (!token) {
        alert("Acceso denegado. Por favor, inicia sesión.");
        window.location.href = 'index.html';
        return; // Detenemos la ejecución del script
    }

    // Elementos principales del DOM
    const navLinks = document.querySelectorAll('.sidebar ul li a');
    const contentArea = document.getElementById('content-area');
    const contentTitle = document.getElementById('content-title');
    const logoutButton = document.getElementById('logout-button');
    
    // Configuración de los endpoints de la API
    const API_BASE_URL = 'http://localhost:8080';

    // --- 2. MANEJO DE LA NAVEGACIÓN ---
    
    // Listener para los enlaces del menú
    document.getElementById('nav-ciudades').addEventListener('click', (e) => handleNavClick(e, 'ciudades'));
    document.getElementById('nav-peliculas').addEventListener('click', (e) => handleNavClick(e, 'peliculas'));

    function handleNavClick(event, section) {
        event.preventDefault(); // Evita que el enlace recargue la página
        navLinks.forEach(link => link.classList.remove('active'));
        event.target.classList.add('active');
        
        if (section === 'ciudades') {
            loadCiudades();
        } else if (section === 'peliculas') {
            loadPeliculas();
        }
    }
    
    // Listener para el botón de cerrar sesión
    logoutButton.addEventListener('click', () => {
        localStorage.removeItem('jwt_token');
        alert("Has cerrado la sesión.");
        window.location.href = 'index.html';
    });

    // --- 3. LÓGICA PARA CARGAR Y MOSTRAR DATOS (CRUD) ---

    // Función para cargar y mostrar la tabla de CIUDADES
    async function loadCiudades() {
        contentTitle.textContent = 'Gestionar Ciudades';
        const ciudades = await fetchAPI('/api/ciudades');
        if (!ciudades) return;

        const tableHTML = `
            <button class="btn-primary" onclick="openModalForCreate('ciudad')">Añadir Nueva Ciudad</button>
            <table class="content-table">
                <thead><tr><th>ID</th><th>Nombre</th><th>Acciones</th></tr></thead>
                <tbody>
                    ${ciudades.map(c => `
                        <tr>
                            <td>${c.id}</td>
                            <td>${c.nombre}</td>
                            <td class="actions">
                                <a class="edit" onclick="openModalForEdit('ciudad', ${c.id})">Editar</a>
                                <a class="delete" onclick="handleDelete('ciudad', ${c.id})">Eliminar</a>
                            </td>
                        </tr>`).join('')}
                </tbody>
            </table>`;
        contentArea.innerHTML = tableHTML;
    }

    // Función para cargar y mostrar la tabla de PELÍCULAS
    async function loadPeliculas() {
        contentTitle.textContent = 'Gestionar Películas';
        const peliculas = await fetchAPI('/api/peliculas');
        if (!peliculas) return;

        const tableHTML = `
            <button class="btn-primary" onclick="openModalForCreate('pelicula')">Añadir Nueva Película</button>
            <table class="content-table">
                <thead><tr><th>ID</th><th>Título</th><th>Clasificación</th><th>Acciones</th></tr></thead>
                <tbody>
                    ${peliculas.map(p => `
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.titulo}</td>
                            <td>${p.clasificacion}</td>
                            <td class="actions">
                                <a class="edit" onclick="openModalForEdit('pelicula', ${p.id})">Editar</a>
                                <a class="delete" onclick="handleDelete('pelicula', ${p.id})">Eliminar</a>
                            </td>
                        </tr>`).join('')}
                </tbody>
            </table>`;
        contentArea.innerHTML = tableHTML;
    }

    // --- 4. LÓGICA DEL MODAL (AÑADIR Y EDITAR) ---

    const modalContainer = document.getElementById('modal-container');
    const modalTitle = document.getElementById('modal-title');
    const modalForm = document.getElementById('modal-form');
    const formFields = document.getElementById('form-fields');
    
    // Hacemos las funciones del modal globales para que los botones onclick las puedan llamar
    window.openModalForCreate = (type) => {
        modalForm.reset();
        modalForm.dataset.id = '';
        modalForm.dataset.type = type;
        modalTitle.textContent = type === 'ciudad' ? 'Añadir Nueva Ciudad' : 'Añadir Nueva Película';
        generateFormFields(type);
        modalContainer.style.display = 'flex';
    };

    window.openModalForEdit = async (type, id) => {
        modalForm.reset();
        modalForm.dataset.id = id;
        modalForm.dataset.type = type;
        const endpoint = `/api/${type === 'ciudad' ? 'ciudades' : 'peliculas'}/${id}`;
        const data = await fetchAPI(endpoint);
        if (!data) return;

        modalTitle.textContent = type === 'ciudad' ? `Editar Ciudad #${id}` : `Editar Película #${id}`;
        generateFormFields(type, data);
        modalContainer.style.display = 'flex';
    };

    function generateFormFields(type, data = {}) {
        if (type === 'ciudad') {
            formFields.innerHTML = `
                <div class="form-group">
                    <label for="nombre">Nombre</label>
                    <input type="text" id="nombre" name="nombre" value="${data.nombre || ''}" required>
                </div>`;
        } else if (type === 'pelicula') {
            formFields.innerHTML = `
                <div class="form-group"><label for="titulo">Título</label><input type="text" id="titulo" name="titulo" value="${data.titulo || ''}" required></div>
                <div class="form-group"><label for="sinopsis">Sinopsis</label><textarea id="sinopsis" name="sinopsis" rows="4">${data.sinopsis || ''}</textarea></div>
                <div class="form-group"><label for="clasificacion">Clasificación</label><input type="text" id="clasificacion" name="clasificacion" value="${data.clasificacion || ''}"></div>
                <div class="form-group"><label for="duracionMinutos">Duración (minutos)</label><input type="number" id="duracionMinutos" name="duracionMinutos" value="${data.duracionMinutos || ''}"></div>
                <div class="form-group"><label for="urlPoster">URL del Póster</label><input type="text" id="urlPoster" name="urlPoster" value="${data.urlPoster || ''}"></div>`;
        }
    }

    // Cerrar el modal
    document.querySelector('.close-modal-button').addEventListener('click', () => modalContainer.style.display = 'none');
    modalContainer.addEventListener('click', (e) => { if(e.target === modalContainer) modalContainer.style.display = 'none'; });

    // Enviar el formulario del modal
    modalForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = e.target.dataset.id;
        const type = e.target.dataset.type;
        const endpoint = `/api/${type === 'ciudad' ? 'ciudades' : 'peliculas'}${id ? '/' + id : ''}`;
        const method = id ? 'PUT' : 'POST';
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());

        const result = await fetchAPI(endpoint, method, data);
        if (result) {
            modalContainer.style.display = 'none';
            if (type === 'ciudad') loadCiudades(); else loadPeliculas();
        }
    });

    // Función para eliminar un registro
    window.handleDelete = async (type, id) => {
        if (confirm(`¿Estás seguro de que quieres eliminar este elemento? Esta acción es irreversible.`)) {
            const endpoint = `/api/${type === 'ciudad' ? 'ciudades' : 'peliculas'}/${id}`;
            const result = await fetchAPI(endpoint, 'DELETE');
            if (result !== null) { // DELETE exitoso no devuelve cuerpo
                 if (type === 'ciudad') loadCiudades(); else loadPeliculas();
            }
        }
    };

    // --- 5. FUNCIÓN CENTRAL PARA COMUNICARSE CON LA API ---
    async function fetchAPI(endpoint, method = 'GET', data = null) {
        const url = `${API_BASE_URL}${endpoint}`;
        const options = {
            method,
            headers: {
                'Authorization': `Bearer ${token}`, // ¡Aquí se usa el token de seguridad!
                'Content-Type': 'application/json'
            }
        };

        if (data) options.body = JSON.stringify(data);

        try {
            const response = await fetch(url, options);
            if (!response.ok) {
                if (response.status === 403) { // 403 Forbidden es común para problemas de token
                    alert('Tu sesión ha expirado o es inválida. Serás redirigido al login.');
                    logoutButton.click();
                }
                throw new Error(`Error ${response.status}: ${response.statusText}`);
            }
            if (response.status === 204) return {}; // 204 No Content para DELETE exitoso
            return await response.json();
        } catch (error) {
            console.error('Error en la llamada a la API:', error);
            alert('Ocurrió un error al comunicarse con el servidor. Revisa la consola para más detalles.');
            return null;
        }
    }
});