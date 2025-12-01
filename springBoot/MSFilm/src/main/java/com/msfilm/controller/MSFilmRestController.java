package com.msfilm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.actors.*;
import com.msfilm.controller.Managers.MainFilmManager;
import com.msfilm.controller.entities.Film;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller of type RestController
 * It sends objects to other microservices or is used as endpoints.
 */
@RestController
@RequestMapping("/api")
public class MSFilmRestController{

    @Autowired
    private MainFilmManager filmManager;

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
            Actor filmAsActor = filmManager.getExactFilm(name);
			Film film = (Film) filmAsActor;
            return film;
        } catch (Exception e) {
            return null;
        }
    }
}