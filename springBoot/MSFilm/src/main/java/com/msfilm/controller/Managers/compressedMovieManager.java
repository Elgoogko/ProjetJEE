package com.msfilm.controller.Managers;

import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Exceptions.SearchException;
import com.actors.Actor;
import com.actors.ActorManager;
import com.actors.Status;
import com.msfilm.DTO.CompressedFilmDTO;
import com.msfilm.controller.entities.CompressedFilm;
import com.msfilm.doe.DOE;

@Component
public class compressedMovieManager extends ActorManager {

    @Autowired
    private DOE doeInstance;
    private String lastSearch = "";
    private int currentPage = 0;

    public compressedMovieManager() {
        super(new ActorManager.Builder().autoID(false).maximumNumberOfActors(1000));
    }

    /**
     * Return a list of compressed movie, based on a research and a page
     * 
     * @param filmName Film name
     * @param page     search page
     * @return
     * @throws Exception
     */
    public HashSet<CompressedFilm> getSearchResult(String filmName, int page) throws Exception {
        if (page > 100 || page < 1) {
            throw new SearchException("Can't go higher than 100 pages or less than 1");
        }
        if (this.lastSearch.equals(filmName) && this.currentPage == page) {
            return this.CastToCompressedMovies();
        }

        this.lastSearch = filmName;
        this.currentPage = page;

        this.getAllActors().clear();

        @SuppressWarnings("unchecked")
        JSONArray second = new JSONArray((List<Object>) doeInstance.getSearchResult(filmName, page).get("Search"));

        for (int i = 0; i <= second.length() - 1; i++) {
            JSONObject temp = second.getJSONObject(i);
            CompressedFilm compressedFilm = new CompressedFilm(
                    temp.optString("Title", ""),
                    temp.optInt("Year", 0),
                    temp.optString("Poster", ""),
                    temp.optString("imdbID", ""));
            compressedFilm.setStatus(Status.STANDBY);
            compressedFilm.setID(compressedFilm.getImdbID());
            this.addActor(compressedFilm);
        }
        return this.CastToCompressedMovies();
    }

    public HashSet<CompressedFilm> CastToCompressedMovies() {
        HashSet<CompressedFilm> result = new HashSet<>();
        for (Actor a : this.getAllActors()) {
            if (a instanceof CompressedFilm cf) {
                result.add(cf);
            }
        }
        return result;
    }

    public HashSet<CompressedFilmDTO> toDTOSet(HashSet<CompressedFilm> films) {
        HashSet<CompressedFilmDTO> dtos = new HashSet<>();
        for (CompressedFilm film : films) {
            dtos.add(new CompressedFilmDTO(
                    film.getTitle(),
                    film.getReleaseYear(),
                    film.getLinkToPoster(),
                    film.getImdbID()));
        }
        return dtos;
    }

}
