package com.msfilm;

import org.springframework.web.bind.annotation.RestController;

import com.Exceptions.CommentServiceException;
import com.Exceptions.SearchException;
import com.actors.*;
import com.exceptions.ActorManagerException;
import com.msfilm.DTO.CompressedFilmDTO;
import com.msfilm.DTO.MovieDTO;
import com.msfilm.controller.Managers.MovieManager;
import com.msfilm.controller.Managers.compressedMovieManager;
import com.msfilm.controller.entities.Comment;
import com.msfilm.controller.entities.CompressedFilm;
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

import org.json.JSONArray;
import org.json.JSONObject;
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

    @GetMapping("/getCommentsByFilm")
    public ResponseEntity<MessageDTO> getComments(@RequestParam String filmId) {
        try {
            List<Comment> comments = commentService.getCommentsByDate(filmId, Order.DSC);

            JSONArray jsonComments = new JSONArray();
            for (Comment c : comments) {
                jsonComments.put(c.CastToJSON());
            }

            JSONObject json = new JSONObject();
            json.put("comments", jsonComments);

            MessageDTO dto = new MessageDTO(
                    "msfilm", // sender = microservice
                    "client", // receiver = client (pas un acteur !)
                    json);

            return ResponseEntity.ok(dto);
        } catch (ActorManagerException e) {
            return ResponseEntity.internalServerError().body(null);
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
    public ResponseEntity<MovieDTO> getMovieByID(@RequestParam String imdbID) {
        try {
            Film f = filmManager.getFilmByIMDBID(imdbID);
            return ResponseEntity.ok(f.toDTO());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/getCompressedFilm")
    public ResponseEntity<HashSet<CompressedFilmDTO>> getCompressedfilm(@RequestParam String name,
            @RequestParam int page) {
        try {
            HashSet<CompressedFilm> films = cMM.getSearchResult(name, page);
            HashSet<CompressedFilmDTO> dtos = cMM.toDTOSet(films);
            return ResponseEntity.ok(dtos);
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