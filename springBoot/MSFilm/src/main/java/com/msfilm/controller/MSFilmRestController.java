package com.msfilm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.actor.Actor;
import com.msfilm.controller.entities.Film;
import com.msfilm.msfilm.MainFilm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller of type RestController
 * It sends objects to other microservices or is used as endpoints.
 */
@RestController
@RequestMapping("/api")
public class MSFilmRestController{


    
    @PostMapping("/commentMovie")
    public String postMethodName(@RequestBody String entity) {        
        return entity;
    }

    /**
     * Return the movie in JSON format
     * @param name movie name
     * @return JSON of the actor Movie
     */
    @GetMapping("/getMovieAsJSON")
    public Film getMethodName(@RequestParam String name) {
        try {
            // Appelle ta méthode existante pour récupérer les données du film
            Actor filmAsActor = MainFilm.getInstance().searchFilm(name);
			Film film = (Film) filmAsActor;
            return film;
        } catch (Exception e) {
            return null;
        }
    }
}