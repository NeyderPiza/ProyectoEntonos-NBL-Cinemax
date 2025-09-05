package com.uis.entornos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Peliculas")
@Data
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private int id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "sinopsis", columnDefinition = "TEXT") // Usamos columnDefinition para tipos de dato largos como TEXT
    private String sinopsis;

    @Column(name = "clasificacion", length = 10)
    private String clasificacion;

    @Column(name = "duracion_minutos")
    private int duracionMinutos;

    @Column(name = "url_poster", length = 255)
    private String urlPoster;

}