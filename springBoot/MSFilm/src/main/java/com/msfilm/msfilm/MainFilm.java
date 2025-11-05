package com.msfilm.msfilm;

import java.util.ArrayList;

import com.actor.*;
import com.msfilm.doe.DOE;
import com.msfilm.film.Film;

/**
 * This class handle the search, the creation and the destruction of Actor of type "Film"
 */
public class MainFilm implements Actor{
    
    private ArrayList<Film> listFilm = null;
    private static Actor instance = new MainFilm(); // thread-safe method
    private static DOE doeInstance = DOE.getInstance();
    
    public static Actor getInstance(){
        if(instance == null){
            instance = new MainFilm();
        }
        return instance;
    }

    private MainFilm(){
        this.listFilm = new ArrayList<>();
    }

    @Override
    public void sendWithActor(String message, Actor actor){

    }

    @Override
    public void receiveWithActor(String message, Actor actor){

    }

    @Override
    public void sendWithId(String message, int id){

    }

    @Override
    public void receiveWithId(String message, int id){

    }

    @Override
    public boolean createNewSubActor(){
        return true;
    }

    @Override
    public boolean deleteSubActor(int id){
        return true;
    }


    @Override
    public boolean thisSubActorExist(int id){
        return true;
    }

    @Override
    public int getId(){
        return 1;
    }
    
}
