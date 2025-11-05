package com.msfilm.film;

import com.actor.Actor;

public class Film implements Actor {
    private Integer idFilm;
    private String title;
    private String description;
    private float score;

    public Film(Integer id, String title, String description, float score){
        this.idFilm = id;
        this.title = title;
        this.description=description;
        this.score=score;
    }

    @Override
    public boolean createNewSubActor() {
        throw new IllegalCallerException("Sub actor Film is not allowed to create another Sub actor");
    }

    @Override
    public boolean deleteSubActor(int arg0) {
        throw new IllegalCallerException("Sub actor Film is not allowed to delete another sub actor");
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public void receiveWithActor(String arg0, Actor arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveWithActor'");
    }

    @Override
    public void receiveWithId(String arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveWithId'");
    }

    @Override
    public void sendWithActor(String arg0, Actor arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendWithActor'");
    }

    @Override
    public void sendWithId(String arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendWithId'");
    }

    @Override
    public boolean thisSubActorExist(int arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'thisSubActorExist'");
    }

    @Override
    public String toString(){
        return "Film id: "+this.idFilm+"; Title: "+this.title+"; Desc.: "+this.description+"; Score : "+this.score;
    }
    
}
