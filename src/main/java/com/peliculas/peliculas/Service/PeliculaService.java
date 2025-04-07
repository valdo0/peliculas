package com.peliculas.peliculas.Service;

import java.util.List;
import java.util.Optional;

import com.peliculas.peliculas.Model.Pelicula;

public interface PeliculaService {
    List <Pelicula> getAllPeliculas();
    Optional<Pelicula> getPeliculaById(Long id);
    Pelicula createPelicula(Pelicula pelicula);
    void deletePelicula(Long id);
    Pelicula updatePelicula(Long id, Pelicula pelicula);
}
