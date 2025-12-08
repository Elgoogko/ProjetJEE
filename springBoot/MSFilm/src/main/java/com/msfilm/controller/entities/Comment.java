package com.msfilm.controller.entities;

import java.time.LocalDateTime;

import org.json.JSONObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idMovie", nullable = false)
    private String idMovie;

    @Column(name = "idUSer", nullable = false)
    private String idUser;

    @Column(name = "")
    @NotBlank(message = "le commentaire ne peut être blanc (que des espaces)")
    @NotNull(message = "le commentaire est de type null : invalide !")
    @NotEmpty(message = "le commentaire ne peut être vide")
    private String comment;

    @Column(name = "score", nullable = false)
    @NotNull(message = "le score doit être un flottant !")
    @Min(value = 0, message = "Le score doit être strictement positif")
    @Max(value = 5, message = "Le score doit être compris entre 0 et 5")
    private double score;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public Comment(String idMovie, String idUser, String comment, double score, LocalDateTime date) {
        this.idMovie = idMovie;
        this.idUser = idUser;
        this.comment = comment;
        this.score = score;
        this.date = date;
    }

    public JSONObject CastToJSON() {
        JSONObject jsonC = new JSONObject();
        jsonC.put("id", this.getId());
        jsonC.put("idMovie", this.getIdMovie());
        jsonC.put("idUser", this.getIdUser());
        jsonC.put("comment", this.getComment());
        jsonC.put("score", this.getScore());
        jsonC.put("date", this.getDate().toString()); // LocalDateTime -> String
        return jsonC;
    }
}
