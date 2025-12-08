package com.msfilm.services;

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

    /**
     * Delete a specific comment by it's ID
     * 
     * @param id id of the comment
     * @throws CommentServiceException
     */
    public void deleteComment(Long id) throws CommentServiceException {
        if (id == null) {
            throw new CommentServiceException("ID comment cannot be null");
        }
        cr.deleteById(id);
    }

    /**
     * Get a specific comment based on it's unique ID.
     * It is used as test purposes
     * 
     * @param id ID of the comment
     * @return
     * @throws CommentServiceException
     */
    public Optional<Comment> getComment(Long id) throws CommentServiceException {
        if (id == null) {
            throw new CommentServiceException("ID comment cannot be null");
        }
        return cr.findById(id);
    }

    /**
     * Get comments on a specific film
     * 
     * @param filmId String
     * @return Comments on this film
     * @throws CommentServiceException
     */
    public List<Comment> getCommentByFilmID(String filmId) throws CommentServiceException {
        if (filmId == null) {
            throw new CommentServiceException("ID comment cannot be null");
        }
        return cr.findByIdMovie(filmId);
    }

    /**
     * Get comments at a specific date
     * 
     * @param localDateTime
     * @return
     * @throws CommentServiceException
     */
    public List<Comment> getCommentByDate(LocalDateTime localDateTime) throws CommentServiceException {
        return cr.findByDate(localDateTime);
    }

    /**
     * get all the comments posted by a specific user
     * 
     * @param userId String
     * @return a list of comments from this user
     * @throws CommentServiceException
     */
    public List<Comment> getCommentByUserId(String userId) throws CommentServiceException {
        return cr.findByIdUser(userId);
    }
}