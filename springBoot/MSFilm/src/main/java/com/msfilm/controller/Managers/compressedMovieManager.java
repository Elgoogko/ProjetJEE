package com.msfilm.controller.Managers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.actors.ActorManager;
import com.msfilm.controller.entities.CompressedFilm;
import com.msfilm.doe.DOE;

@Component
public class compressedMovieManager extends ActorManager {
    @Autowired
    private static DOE doeInstance;
    private String lastSearch = "";
    private int lastPage = 1;

    public compressedMovieManager(){
        super(new ActorManager.Builder().autoID(false));
    }

    public ArrayList<CompressedFilm> getSearchResult(String filmName, int page) throws Exception{
        this.lastSearch = filmName;
        this.lastPage = page;
        
        doeInstance.getSearchResult(filmName, page);
        return null;
    }

    public ArrayList<CompressedFilm> getNextSearchResult(int page){
        if(lastSearch.isBlank() || lastSearch.isEmpty()){
            throw new IllegalArgumentException();
        }

        return null;
    }
}
