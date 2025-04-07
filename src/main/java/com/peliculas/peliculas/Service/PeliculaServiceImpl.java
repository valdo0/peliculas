package com.peliculas.peliculas.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peliculas.peliculas.Model.Pelicula;
import com.peliculas.peliculas.Repository.PeliculaRepository;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @Override
    public List<Pelicula> getAllPeliculas() {
        return peliculaRepository.findAll();
    }

    @Override
    public Optional<Pelicula> getPeliculaById(Long id) {
        return peliculaRepository.findById(id);
    }

    @Override
    public Pelicula createPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }
    @Override
    public void deletePelicula(Long id) {
        peliculaRepository.deleteById(id);
    }
    @Override
    public Pelicula updatePelicula(Long id, Pelicula pelicula) {
        if (peliculaRepository.existsById(id)) {
            pelicula.setId(id);
            return peliculaRepository.save(pelicula);
        } else {
            return null;
        }
    }
}
