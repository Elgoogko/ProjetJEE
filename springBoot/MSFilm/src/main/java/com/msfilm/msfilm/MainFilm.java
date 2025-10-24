package com.msfilm.msfilm;

import java.util.*;
import com.actor.*;


/**
 * Singleton actor
 * 
 * 
 */
public class MainFilm implements Actor{

    private int id;
    private ArrayList<Actor> lstFilm;

    public MainFilm(){
        this.id = 0; // on lui met l'id 0
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
        return this.id;
    }
    
}
