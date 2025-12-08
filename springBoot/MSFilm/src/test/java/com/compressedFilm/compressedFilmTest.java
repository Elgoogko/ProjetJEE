package com.compressedFilm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.msfilm.controller.Managers.compressedMovieManager;

public class compressedFilmTest {
    @Autowired
    private compressedMovieManager cMM;

    @DisplayName("Juste checking")
    @Test
    public void justTest() {
        try {
            cMM.getSearchResult("Avengers", 0);
        } catch (Exception e) {
        }
    }
}
