package com.peliculas.peliculas.Repository;

import com.peliculas.peliculas.Model.Pelicula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PeliculaRepositoryTest {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Test
    void testSaveAndFindById() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Titanic");
        pelicula.setAño(1997);
        pelicula.setDirector("James Cameron");
        pelicula.setGenero("Drama");
        pelicula.setSinopsis("Una historia de amor en el Titanic.");

        Pelicula saved = peliculaRepository.save(pelicula);

        assertNotNull(saved.getId());

        Optional<Pelicula> found = peliculaRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Titanic", found.get().getTitulo());
    }

    @Test
    void testDelete() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Avatar");
        pelicula.setAño(2009);
        pelicula.setDirector("James Cameron");
        pelicula.setGenero("Ciencia Ficción");
        pelicula.setSinopsis("Una aventura en Pandora.");

        Pelicula saved = peliculaRepository.save(pelicula);

        Long id = saved.getId();
        peliculaRepository.deleteById(id);

        Optional<Pelicula> found = peliculaRepository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll() {
        Pelicula pelicula1 = new Pelicula();
        pelicula1.setTitulo("Titanic");
        pelicula1.setAño(1997);
        pelicula1.setDirector("James Cameron");
        pelicula1.setGenero("Drama");
        pelicula1.setSinopsis("Una historia de amor en el Titanic.");

        Pelicula pelicula2 = new Pelicula();
        pelicula2.setTitulo("Avatar");
        pelicula2.setAño(2009);
        pelicula2.setDirector("James Cameron");
        pelicula2.setGenero("Ciencia Ficción");
        pelicula2.setSinopsis("Una aventura en Pandora.");

        peliculaRepository.save(pelicula1);
        peliculaRepository.save(pelicula2);

        var peliculas = peliculaRepository.findAll();
        assertEquals(2, peliculas.size());
    }
}
