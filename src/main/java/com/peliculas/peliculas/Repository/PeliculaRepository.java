package com.peliculas.peliculas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peliculas.peliculas.Model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    
}
