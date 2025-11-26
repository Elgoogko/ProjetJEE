package com.msfilm.msfilm;

import java.util.ArrayList;
import java.util.Map;

import com.actor.*;
import com.msfilm.controller.entities.Film;
import com.msfilm.doe.DOE;

/**
 * This class handle the search, the creation and the destruction of Actor of
 * type "Film"
 */
public class MainFilm {

    private ArrayList<Film> listFilm = null;
    private static MainFilm instance = new MainFilm(); // thread-safe method
    private static DOE doeInstance = DOE.getInstance();

    /**
     * Singleton methode
     * 
     * @return unique instance of MainFilm
     */
    public static MainFilm getInstance(){
        if(instance == null){
            instance = new MainFilm();
        }
        return instance;
    }

    private MainFilm() {
        this.listFilm = new ArrayList<>();
    }

    private int getUniqueID() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (Film f : this.listFilm) {
            temp.add(f.getId());
        }
        Integer newId = 0;
        while (temp.contains(newId)) {
            newId++;
        }
        return newId;
    }

    /**
     * Create a new actor of type Film according to a name
     * Or return null if the movie does not exists / blank / null value
     * 
     * @param movieName the movie name
     * @return an Actor of type movie
     */
    public Film searchFilm(String movieName) {
        try {
            Map<String, Object> movie = doeInstance.getFilm(movieName);
            Film newMovie = new Film(getUniqueID(), 0, movie);
            listFilm.add(newMovie);
            return newMovie;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Film incconue : essayer d'écrire le titre exacte");
            return null;
        }
    }
}