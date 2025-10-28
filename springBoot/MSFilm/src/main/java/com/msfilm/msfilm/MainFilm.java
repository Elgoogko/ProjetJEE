package com.msfilm.msfilm;

import com.actor.*;

public class MainFilm implements Actor{

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
