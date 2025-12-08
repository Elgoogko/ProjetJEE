package com.msfilm.controller.Managers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.actors.ActorManager;
import com.msfilm.controller.entities.Film;
import com.msfilm.controller.entities.CompressedFilm;
import com.msfilm.doe.DOE;
import com.msfilm.doe.searchType;

/**
 * This class handle the search, the creation and the destruction of Actor of
 * type "Film"
 */
@Component
public class MovieManager extends ActorManager {
    @Autowired
    private DOE doeInstance;

    public MovieManager() {
        super(new ActorManager.Builder().autoID(false).maximumNumberOfActors(100));
    }

    /**
     * Create a new actor of type Film according to a name
     * Or return null if the movie does not exists / blank / null value
     * 
     * @param movieName the movie name
     * @return an Actor of type movie
     */
    public Film getExactFilm(String movieName) throws Exception {
        Map<String, Object> movie = doeInstance.getFilm(movieName, searchType.EXACT_NAME);
        Film newMovie = new Film(0, movie);
        this.addActor(newMovie);
        return newMovie;
    }

    /**
     * 
     * @param imdbID
     * @return
     */
    public Film getFilmByIMDBID(String imdbID) {
        try {
            Map<String, Object> movie = doeInstance.getFilm(imdbID, searchType.ID);
            Film newMovie = new Film(0, movie);
            this.addActor(newMovie);
            return newMovie;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Film incconu : essayer d'écrire le titre exacte");
            return null;
        }
    }

    public Film castCompressedFilmToFilm(CompressedFilm cFilm) {
        return getFilmByIMDBID(cFilm.getImdbID());
    }
}