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

import com.uis.entornos.modelo.Pelicula;
import com.uis.entornos.repositorio.PeliculaRepository;

@RestController
@RequestMapping("/api/peliculas") // URL base para todo lo relacionado con películas
public class PeliculaControlador {

    @Autowired
    private PeliculaRepository peliculaRepository;

    // --- ENDPOINT PARA CREAR UNA NUEVA PELÍCULA (CREATE) ---
    @PostMapping
    public Pelicula crearPelicula(@RequestBody Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    // --- ENDPOINT PARA OBTENER TODAS LAS PELÍCULAS (READ ALL) ---
    @GetMapping
    public List<Pelicula> obtenerTodasLasPeliculas() {
        return peliculaRepository.findAll();
    }
    
    // --- ENDPOINT PARA OBTENER UNA PELÍCULA POR SU ID (READ ONE) ---
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPeliculaPorId(@PathVariable int id) {
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(id);
        
        if (peliculaOptional.isPresent()) {
            return ResponseEntity.ok(peliculaOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- ENDPOINT PARA ACTUALIZAR UNA PELÍCULA (UPDATE) ---
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizarPelicula(@PathVariable int id, @RequestBody Pelicula peliculaDetalles) {
        Optional<Pelicula> peliculaOptional = peliculaRepository.findById(id);

        if (peliculaOptional.isPresent()) {
            Pelicula peliculaExistente = peliculaOptional.get();
            
            // Actualizamos todos los campos con los nuevos datos
            peliculaExistente.setTitulo(peliculaDetalles.getTitulo());
            peliculaExistente.setSinopsis(peliculaDetalles.getSinopsis());
            peliculaExistente.setClasificacion(peliculaDetalles.getClasificacion());
            peliculaExistente.setDuracionMinutos(peliculaDetalles.getDuracionMinutos());
            peliculaExistente.setUrlPoster(peliculaDetalles.getUrlPoster());
            
            Pelicula peliculaActualizada = peliculaRepository.save(peliculaExistente);
            return ResponseEntity.ok(peliculaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // --- ENDPOINT PARA ELIMINAR UNA PELÍCULA (DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable int id) {
        // Verificamos si la película existe antes de intentar borrarla
        if (peliculaRepository.existsById(id)) {
            peliculaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
