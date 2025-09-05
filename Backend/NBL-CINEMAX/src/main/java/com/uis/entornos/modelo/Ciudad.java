package com.uis.entornos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Le dice a JPA: "Esta clase representa una tabla en la base de datos"
@Table(name = "Ciudades") // Especifica el nombre exacto de la tabla en la BD
@Data // ¡Magia de Lombok! Crea automáticamente getters, setters, toString(), etc.
public class Ciudad {

    @Id // Marca este campo como la clave primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a MySQL que genere el valor (autoincremental)
    @Column(name = "id_ciudad") // Mapea este atributo a la columna "id_ciudad"
    private int id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100) // Mapea a la columna "nombre" y añade restricciones
    private String nombre;

}
