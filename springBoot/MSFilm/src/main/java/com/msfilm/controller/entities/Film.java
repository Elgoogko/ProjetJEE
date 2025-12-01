package com.msfilm.controller.entities;

import java.util.Map;

import com.actors.Actor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film extends Actor {
    /**
     * Movie Score : ratings based on our own data base (not imdb score provided in the data)
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

    @Override
    public String toString(){
        return "Film id: "+this.getId()+"; \nTitle: "+moviedata.get("Title")+"; \nDesc.: "+moviedata.get("Description")+"; Score : "+this.score;
    }

}