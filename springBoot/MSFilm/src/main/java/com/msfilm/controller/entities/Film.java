package com.msfilm.controller.entities;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import com.msfilm.Order;
import com.msfilm.DTO.MovieDTO;
import com.actors.Actor;
import com.actors.Status;
import com.msfilm.services.CommentService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Film extends Actor {
    /**
     * Movie Score : ratings based on our own data base (not imdb score provided in
     * the data)
     */
    @NotNull
    @Min(0)
    @Max(5)
    private float score;

    /**
     * Raw movie Data
     */
    @NotEmpty(message = "Données invalides !")
    @NotNull(message = "Données invalides !")
    private Map<String, Object> moviedata;

    @NotNull(message = "Erreur d'initialisation !")
    private CommentService commentService;

    public Film() {
        this.setLifetime(25000);
        this.setID((String) this.getMoviedata().get("imdbID"));
        this.setStatus(Status.ALIVE);
    }

    public JSONObject castToJsonObject() {
        JSONObject json = new JSONObject();
        json.append("moviedata", this.moviedata);
        json.append("score", this.score);
        return json;
    }

    @Override
    public String toString() {
        return "Film id: " + this.getId() + "; \nTitle: " + moviedata.get("Title") + "; \nDesc.: "
                + moviedata.get("Description") + "; Score : " + this.score;
    }

    @Override
    public void receive(JSONObject message, String senderID) {
        try {
            // message contient par exemple : { "comment": "Super film!", "score": 4,
            // "userId": "user123" }
            Double newScore = message.getDouble("score");
            String userId = message.getString("userId");

            // Enregistrer le commentaire via le service
            commentService.createComment(userId, this.getId(), message.getString("comment"), newScore);

            // Recalculer la moyenne
            List<Comment> allComments = commentService.getCommentByFilmID(this.getId());
            float sum = 0;
            for (Comment c : allComments) {
                sum += c.getScore();
            }
            if (!allComments.isEmpty()) {
                this.score = sum / allComments.size();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject castToJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("moviedata", this.moviedata);
        obj.put("score", score);
        return obj;
    }

    public List<Comment> getAllComments() {
        return this.commentService.getCommentByFilmID(this.getId());
    }

    public MovieDTO toDTO() {
        return new MovieDTO(this.score, this.moviedata);
    }

    public List<Comment> getAllCommentOrdered(Order o) {
        return this.commentService.getCommentsByDate(getId(), o);
    }
}