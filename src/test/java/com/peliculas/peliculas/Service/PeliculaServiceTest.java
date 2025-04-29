package com.peliculas.peliculas.Service;

import com.peliculas.peliculas.Model.Pelicula;
import com.peliculas.peliculas.Repository.PeliculaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PeliculaServiceTest {

    @Mock
    private PeliculaRepository peliculaRepository;

    @InjectMocks
    private PeliculaServiceImpl peliculaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPeliculas() {
        Pelicula pelicula1 = createPelicula(1L, "Titanic", 1997, "James Cameron", "Drama", "Una historia de amor");
        Pelicula pelicula2 = createPelicula(2L, "Avatar", 2009, "James Cameron", "Ciencia Ficción", "Aventura en Pandora");

        when(peliculaRepository.findAll()).thenReturn(Arrays.asList(pelicula1, pelicula2));

        List<Pelicula> result = peliculaService.getAllPeliculas();

        assertEquals(2, result.size());
        verify(peliculaRepository, times(1)).findAll();
    }

    @Test
    void testGetPeliculaById() {
        Pelicula pelicula = createPelicula(1L, "Titanic", 1997, "James Cameron", "Drama", "Una historia de amor");

        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));

        Optional<Pelicula> result = peliculaService.getPeliculaById(1L);

        assertTrue(result.isPresent());
        assertEquals("Titanic", result.get().getTitulo());
        verify(peliculaRepository, times(1)).findById(1L);
    }

    @Test
    void testCreatePelicula() {
        Pelicula pelicula = createPelicula(null, "Nueva Pelicula", 2024, "Nuevo Director", "Acción", "Sinopsis");

        when(peliculaRepository.save(pelicula)).thenReturn(createPelicula(1L, "Nueva Pelicula", 2024, "Nuevo Director", "Acción", "Sinopsis"));

        Pelicula result = peliculaService.createPelicula(pelicula);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(peliculaRepository, times(1)).save(pelicula);
    }

    @Test
    void testDeletePelicula() {
        Long id = 1L;

        doNothing().when(peliculaRepository).deleteById(id);

        peliculaService.deletePelicula(id);

        verify(peliculaRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdatePelicula() {
        Long id = 1L;
        Pelicula pelicula = createPelicula(null, "Titanic 2", 2025, "James Cameron", "Drama", "Secuela de Titanic");

        Pelicula existing = createPelicula(id, "Titanic", 1997, "James Cameron", "Drama", "Original");
        when(peliculaRepository.findById(id)).thenReturn(Optional.of(existing));

        when(peliculaRepository.save(any(Pelicula.class))).thenAnswer(invocation -> {
            Pelicula p = invocation.getArgument(0);
            p.setId(id);
            return p;
        });

        Pelicula result = peliculaService.updatePelicula(id, pelicula);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Titanic 2", result.getTitulo());
        verify(peliculaRepository, times(1)).save(any(Pelicula.class));
        verify(peliculaRepository, times(1)).findById(id);
}


    private Pelicula createPelicula(Long id, String titulo, int año, String director, String genero, String sinopsis) {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(id);
        pelicula.setTitulo(titulo);
        pelicula.setAño(año);
        pelicula.setDirector(director);
        pelicula.setGenero(genero);
        pelicula.setSinopsis(sinopsis);
        return pelicula;
    }
}
