package com.msfilm;

import org.springframework.web.bind.annotation.RestController;

import com.Exceptions.CommentServiceException;
import com.Exceptions.SearchException;
import com.actors.*;
import com.exceptions.ActorManagerException;
import com.msfilm.controller.Managers.MovieManager;
import com.msfilm.controller.Managers.compressedMovieManager;
import com.msfilm.controller.entities.Comment;
import com.msfilm.controller.entities.Film;
import com.msfilm.services.CommentService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    private MovieManager filmManager;

    @Autowired
    private compressedMovieManager cMM;

    @Autowired
    private CommentService commentService;

    @GetMapping("/getCommentsSingleParam")
    public ResponseEntity<List<Comment>> getComment(@RequestParam(required = false) String filmId,
            @RequestParam(required = false) String userId) {
        try {
            List<Comment> comments = new ArrayList<>();
            if (filmId != null) {
                comments = commentService.getCommentByFilmID(filmId);
            } else if (userId != null) {
                comments = commentService.getCommentByFilmID(userId);
            }

            return ResponseEntity.ok(comments);
        } catch (CommentServiceException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @GetMapping("/getCommentsByID")
    public ResponseEntity<Optional<Comment>> getCommentByID(@RequestParam Long id) {
        try {
            Optional<Comment> comments = commentService.getComment(id);
            return ResponseEntity.ok(comments);
        } catch (CommentServiceException e) {
            return ResponseEntity.badRequest().body(null);

        }
    }

    /**
     * Return the movie in JSON format
     * 
     * @param name movie name
     * @return JSON of the actor Movie
     */
    @GetMapping("/getMovieByName")
    public ResponseEntity<Actor> getMethodName(@RequestParam String name) {
        try {
            Actor filmAsActor = filmManager.getExactFilm(name);
            return ResponseEntity.ok(filmAsActor);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/getMovieByID")
    public ResponseEntity<MessageDTO> getMovieByID(@RequestParam String imdbID, @RequestParam String userID) {
        try {
            Film f = filmManager.getFilmByIMDBID(imdbID);
            MessageDTO message = new MessageDTO(userID, f.getId(), f.castToJSONObject());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/getCompressedFilm")
    public ResponseEntity<HashSet<Actor>> getCompressedfilm(@RequestParam String name, @RequestParam int page) {
        try {
            HashSet<Actor> actors = cMM.getSearchResult(name, page);
            return ResponseEntity.ok(actors);
        } catch (SearchException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception f) {
            f.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/postComment")
    public ResponseEntity<String> sendMessageToFilm(@RequestBody MessageDTO message) {
        try {
            filmManager.transferToActorLocal(message);
            return ResponseEntity.ok(null);
        } catch (ActorManagerException e) {
            filmManager.getFilmByIMDBID(message.receiverId);
            filmManager.transferToActorLocal(message);
            return ResponseEntity.ok(null);
        }
    }
}