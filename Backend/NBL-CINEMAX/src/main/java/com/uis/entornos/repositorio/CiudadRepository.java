package com.uis.entornos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uis.entornos.modelo.Ciudad;

@Repository // Le dice a Spring que esta es una interfaz de repositorio
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    // JpaRepository<ClaseDeLaEntidad, TipoDeDatoDeLaClavePrimaria>
}
