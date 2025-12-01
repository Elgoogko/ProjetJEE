package com.msfilm.controller.Managers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.actors.ActorManager;
import com.msfilm.controller.entities.Film;
import com.msfilm.doe.DOE;
import com.msfilm.doe.searchType;

/**
 * This class handle the search, the creation and the destruction of Actor of
 * type "Film"
 */
@Component
public class MainFilmManager extends ActorManager {    
    @Autowired
    private static DOE doeInstance;

    private MainFilmManager() {
        super(new ActorManager.Builder().actorIdentifier("F").autoID(true).maximumNumberOfActors(100));
    }

    /**
     * Create a new actor of type Film according to a name
     * Or return null if the movie does not exists / blank / null value
     * 
     * @param movieName the movie name
     * @return an Actor of type movie
     */
    public Film getExactFilm(String movieName) {
        try {
            Map<String, Object> movie = doeInstance.getFilm(movieName, searchType.EXACT_NAME);
            Film newMovie = new Film(0, movie);
            newMovie.setLifetime(25000);
            this.addActor(newMovie);
            return newMovie;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Film incconu : essayer d'écrire le titre exacte");
            return null;
        }
    }
}