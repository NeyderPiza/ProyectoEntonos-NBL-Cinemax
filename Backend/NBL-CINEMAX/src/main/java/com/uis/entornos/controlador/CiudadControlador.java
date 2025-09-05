package com.uis.entornos.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uis.entornos.modelo.Ciudad;
import com.uis.entornos.repositorio.CiudadRepository;

@RestController // Anotación que combina @Controller y @ResponseBody. Esencial para APIs REST.
@RequestMapping("/api/ciudades") // Define la URL base para todos los endpoints en esta clase.
public class CiudadControlador {

    @Autowired // Inyección de dependencias: Spring nos da una instancia de CiudadRepository.
    private CiudadRepository ciudadRepository;

    // --- ENDPOINT PARA CREAR UNA NUEVA CIUDAD (CREATE) ---
    @PostMapping
    public Ciudad crearCiudad(@RequestBody Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    // --- ENDPOINT PARA OBTENER TODAS LAS CIUDADES (READ) ---
    @GetMapping
    public List<Ciudad> obtenerTodasLasCiudades() {
        return ciudadRepository.findAll();
    }

    // --- ENDPOINT PARA ACTUALIZAR UNA CIUDAD (UPDATE) ---
    @PutMapping("/{id}")
    public ResponseEntity<Ciudad> actualizarCiudad(@PathVariable int id, @RequestBody Ciudad ciudadDetalles) {
        Optional<Ciudad> ciudadOptional = ciudadRepository.findById(id);

        if (ciudadOptional.isPresent()) {
            Ciudad ciudadExistente = ciudadOptional.get();
            ciudadExistente.setNombre(ciudadDetalles.getNombre());
            Ciudad ciudadActualizada = ciudadRepository.save(ciudadExistente);
            return ResponseEntity.ok(ciudadActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // --- ENDPOINT PARA ELIMINAR UNA CIUDAD (DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCiudad(@PathVariable int id) {
        Optional<Ciudad> ciudadOptional = ciudadRepository.findById(id);

        if (ciudadOptional.isPresent()) {
            ciudadRepository.delete(ciudadOptional.get());
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content, estándar para DELETE exitoso.
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 Not Found si no existe.
        }
    }
}
