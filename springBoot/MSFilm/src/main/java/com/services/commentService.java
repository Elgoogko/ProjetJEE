package com.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Exceptions.CommentServiceException;
import com.msfilm.controller.entities.Comment;

@Service
public class CommentService {

    @Autowired
    private CommentRepository cr;

    /**
     * INSTER COMMENT
     * 
     * @param idUser  given by the FilmManager
     * @param idMovie given by the MSClient Manager
     * @param comment given at creation, it's a plain text
     * @param score   score between 0 and 5
     */
    public void createComment(String idUser, String idMovie, String comment, Double score) {
        Comment newComment = new Comment(idUser, idMovie, comment, score, LocalDateTime.now());
        cr.save(newComment);
    }

    public void deleteComment(Long id) {
        if (id == null) {
            throw new CommentServiceException("ID comment cannot be null");
        }
        cr.deleteById(id);
    }

    public Optional<Comment> getComment(Long id) {
        if (id == null) {
            throw new CommentServiceException("ID comment cannot be null");
        }
        return cr.findById(id);
    }

    public List<Comment> getCommentsByFilmID(String filmId) {
        if (filmId == null) {
            throw new CommentServiceException("ID comment cannot be null");
        }
        return cr.findByIdMovie(filmId);
    }
}