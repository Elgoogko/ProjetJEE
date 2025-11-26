package com.msfilm.controller.entities;

import java.util.Map;

import com.actor.Actor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film implements Actor {
    /**
     * Actor ID : used to identify the actor during his lifecycle
     */
    @NotNull
    @NotEmpty
    private int idActor;

    /**
     * Movie Score : ratings based on our own data base (not imdb score provided in the data)
     */
    @NotEmpty
    @NotNull
    private float score;

    /**
     * Raw movie Data 
     */
    @NotEmpty(message = "Données invalides !")
    @NotNull(message = "Données invalides !")
    private Map<String, Object> moviedata;

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
        return this.idActor;
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
        return "Film id: "+this.idActor+"; \nTitle: "+moviedata.get("Title")+"; \nDesc.: "+moviedata.get("Description")+"; Score : "+this.score;
    }
    
}
