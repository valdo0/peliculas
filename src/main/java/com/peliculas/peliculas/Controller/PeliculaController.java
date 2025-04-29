package com.peliculas.peliculas.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peliculas.peliculas.Model.Pelicula;
import com.peliculas.peliculas.Service.PeliculaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;




@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    public PeliculaService peliculaService;

    @GetMapping
    public CollectionModel<EntityModel<Pelicula>> getAllPeliculas() {
        List<EntityModel<Pelicula>> peliculas = peliculaService.getAllPeliculas().stream()
            .map(pelicula -> EntityModel.of(pelicula,
                linkTo(methodOn(PeliculaController.class).getPeliculaById(pelicula.getId())).withSelfRel(),
                linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withRel("peliculas")
            ))
            .collect(Collectors.toList());

        return CollectionModel.of(peliculas,
            linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<Pelicula> getPeliculaById(@PathVariable Long id) {
        Pelicula pelicula = peliculaService.getPeliculaById(id)
            .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));

        return EntityModel.of(pelicula,
            linkTo(methodOn(PeliculaController.class).getPeliculaById(id)).withSelfRel(),
            linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withRel("peliculas")
        );
    }

    @PostMapping
    public EntityModel<Pelicula> createPelicula(@RequestBody Pelicula pelicula) {
        Pelicula nueva = peliculaService.createPelicula(pelicula);
        return EntityModel.of(nueva,
            linkTo(methodOn(PeliculaController.class).getPeliculaById(nueva.getId())).withSelfRel()
        );
    }

    @DeleteMapping("/{id}")
    public void deletePelicula(@PathVariable Long id) {
        peliculaService.deletePelicula(id);
    }

    @PutMapping("/{id}")
    public EntityModel<Pelicula> updatePelicula(@PathVariable Long id, @RequestBody Pelicula pelicula) {
        Pelicula actualizada = peliculaService.updatePelicula(id, pelicula);
        return EntityModel.of(actualizada,
            linkTo(methodOn(PeliculaController.class).getPeliculaById(id)).withSelfRel()
        );
    }
}
