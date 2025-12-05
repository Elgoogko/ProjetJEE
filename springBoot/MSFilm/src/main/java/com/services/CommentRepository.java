package com.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msfilm.controller.entities.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIdMovie(String idMovie);

    List<Comment> findByIdUser(String idUser);

    Optional<Comment> findById(Long id);

    void deleteById(Long id);
}
