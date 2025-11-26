package com.msfilm.controller.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
@AllArgsConstructor
public class Comment {
    @Id
    private String idFilm;

    @Id
    private int idUSer;

    @NotBlank(message = "le commentaire ne peut être blanc (que des espaces)")
    @NotNull(message =  "le commentaire est de type null : invalide !")
    @NotEmpty(message = "le commentaire ne peut être vide")
    private String comment; 

    @NotBlank(message = "Un score est obligatoire sinon on poste pas de commentaire")
    @NotNull(message = "le score doit être un flottant !")
    @Min(value = 0, message = "le score doit être strictement positif")
    @Max(value = 5, message = "LE score doit être compris entre 0 et 5")
    private float score;
}
