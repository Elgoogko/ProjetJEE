package com.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msfilm.controller.entities.Comment;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIdMovie(String idMovie);
    List<Comment> findByIdUser(String idUser);
    void deleteById(Long id);
}
