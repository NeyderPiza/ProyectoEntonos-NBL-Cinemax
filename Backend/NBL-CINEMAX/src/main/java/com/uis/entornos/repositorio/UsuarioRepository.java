package com.uis.entornos.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uis.entornos.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Necesitamos un método para buscar un usuario por su email
    Optional<Usuario> findByEmail(String email);
}
