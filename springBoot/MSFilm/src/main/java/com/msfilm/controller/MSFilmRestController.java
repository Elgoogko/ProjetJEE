package com.msfilm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.actors.*;
import com.msfilm.controller.Managers.MainFilmManager;
import com.msfilm.controller.entities.Comment;
import com.msfilm.controller.entities.Film;
import com.services.CommentService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller of type RestController
 * It sends objects to other microservices or is used as endpoints.
 */
@RestController
@RequestMapping("/api")
public class MSFilmRestController {

    @Autowired
    private MainFilmManager filmManager;

    @Autowired
    private CommentService commentService;

    @PostMapping("/postComment")
    public ResponseEntity<String> postComment(@RequestBody Comment entity) {
        try {
            commentService.createComment(entity.getIdUser(), entity.getIdMovie(), entity.getComment(),
                    entity.getScore());
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Return the movie in JSON format
     * 
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

    @GetMapping("/getSearchResultAsJSON")
    public HashSet<Actor> getSearchResult(@RequestParam String name) {
        try {
            filmManager.getSeachResult(name);
            ArrayList<Film> searchResult = new ArrayList<>();
            return filmManager.getAllActors();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/getComment")
    public ArrayList<Comment> getComment(@RequestParam Long id) {
        Optional<Comment> comments = commentService.getComment(id);
        return null;
    }
}