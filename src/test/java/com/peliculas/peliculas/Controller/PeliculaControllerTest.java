package com.peliculas.peliculas.Controller;

import com.peliculas.peliculas.Model.Pelicula;
import com.peliculas.peliculas.Service.PeliculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PeliculaControllerTest {

    @Mock
    private PeliculaService peliculaService;

    @InjectMocks
    private PeliculaController peliculaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPeliculas() {
        Pelicula pelicula1 = createPelicula(1L, "Titanic", 1997, "James Cameron", "Drama", "Una historia de amor en el Titanic.");
        Pelicula pelicula2 = createPelicula(2L, "Avatar", 2009, "James Cameron", "Ciencia Ficción", "Una aventura en Pandora.");

        List<Pelicula> peliculas = Arrays.asList(pelicula1, pelicula2);

        when(peliculaService.getAllPeliculas()).thenReturn(peliculas);

        List<Pelicula> result = peliculaController.getAllPeliculas();

        assertEquals(2, result.size());
        verify(peliculaService, times(1)).getAllPeliculas();
    }

    @Test
    void testGetPeliculaById() {
        Pelicula pelicula = createPelicula(1L, "Titanic", 1997, "James Cameron", "Drama", "Una historia de amor en el Titanic.");
        when(peliculaService.getPeliculaById(1L)).thenReturn(Optional.of(pelicula));

        Optional<Pelicula> result = peliculaController.getPeliculaById(1L);

        assertTrue(result.isPresent());
        assertEquals("Titanic", result.get().getTitulo());
        verify(peliculaService, times(1)).getPeliculaById(1L);
    }

    @Test
    void testCreatePelicula() {
        Pelicula pelicula = createPelicula(null, "Nueva Pelicula", 2024, "Nuevo Director", "Acción", "Sinopsis de prueba");
        Pelicula peliculaGuardada = createPelicula(1L, "Nueva Pelicula", 2024, "Nuevo Director", "Acción", "Sinopsis de prueba");

        when(peliculaService.createPelicula(pelicula)).thenReturn(peliculaGuardada);

        Pelicula result = peliculaController.createPelicula(pelicula);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(peliculaService, times(1)).createPelicula(pelicula);
    }

    @Test
    void testDeletePelicula() {
        Long id = 1L;

        doNothing().when(peliculaService).deletePelicula(id);

        peliculaController.deletePelicula(id);

        verify(peliculaService, times(1)).deletePelicula(id);
    }

    @Test
    void testUpdatePelicula() {
        Long id = 1L;
        Pelicula peliculaActualizada = createPelicula(id, "Titanic 2", 2025, "James Cameron", "Drama", "Secuela de Titanic");

        when(peliculaService.updatePelicula(id, peliculaActualizada)).thenReturn(peliculaActualizada);

        Pelicula result = peliculaController.updatePelicula(id, peliculaActualizada);

        assertNotNull(result);
        assertEquals("Titanic 2", result.getTitulo());
        verify(peliculaService, times(1)).updatePelicula(id, peliculaActualizada);
    }

    // Helper method to create Pelicula objects easily
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
