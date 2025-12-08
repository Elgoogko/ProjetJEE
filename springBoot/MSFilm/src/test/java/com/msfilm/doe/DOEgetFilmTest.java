package com.msfilm.doe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.msfilm.MSFilmApplication;

@SpringBootTest(classes = MSFilmApplication.class)
public class DOEgetFilmTest {
    @Autowired
    private DOE instance;

    private static Map<String, Object> TEST = new HashMap<>();

    @BeforeAll
    static void riseUp() {
        TEST.put("Title", "The Avengers");
        TEST.put("Year", "2012");
        TEST.put("Rated", "PG-13");
        TEST.put("Released", "04 May 2012");
        TEST.put("Runtime", "143 min");
        TEST.put("Genre", "Action, Sci-Fi");
        TEST.put("Director", "Joss Whedon");
        TEST.put("Writer", "Joss Whedon, Zak Penn");
        TEST.put("Actors", "Robert Downey Jr., Chris Evans, Scarlett Johansson");
        TEST.put("Plot",
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.");
        TEST.put("Language", "English, Russian");
        TEST.put("Country", "United States");
        TEST.put("Awards", "Nominated for 1 Oscar. 39 wins & 81 nominations total");
        TEST.put("Poster",
                "https://m.media-amazon.com/images/M/MV5BNGE0YTVjNzUtNzJjOS00NGNlLTgxMzctZTY4YTE1Y2Y1ZTU4XkEyXkFqcGc@._V1_SX300.jpg");

        // Initialisation de la liste des Ratings
        List<Map<String, String>> ratings = Arrays.asList(
                Map.of("Source", "Internet Movie Database", "Value", "8.0/10"),
                Map.of("Source", "Rotten Tomatoes", "Value", "91%"),
                Map.of("Source", "Metacritic", "Value", "69/100"));
        TEST.put("Ratings", ratings);

        TEST.put("Metascore", "69");
        TEST.put("imdbRating", "8.0");
        TEST.put("imdbVotes", "1,531,736");
        TEST.put("imdbID", "tt0848228");
        TEST.put("Type", "movie");
        TEST.put("DVD", "N/A");
        TEST.put("BoxOffice", "$623,357,910");
        TEST.put("Production", "N/A");
        TEST.put("Website", "N/A");
        TEST.put("Response", "True");
    }

    @Test
    @DisplayName("Null test")
    void testNull() {
        assertThrows(NullPointerException.class, () -> {
            instance.getFilm(null, searchType.APPX_SEARCH);
        });
    }

    @Test
    @DisplayName("Empty name test")
    void testEmpty() {
        assertThrows(NullPointerException.class, () -> {
            instance.getFilm("", searchType.APPX_SEARCH);
        });
    }

    @Test
    @DisplayName("Blank name test")
    void testBlank() {
        assertThrows(NullPointerException.class, () -> {
            instance.getFilm("      ", searchType.APPX_SEARCH);
        });
    }

    @Test
    @DisplayName("Simple Film  : Avengers")
    void testSimpleFilm() throws Exception {
        Map<String, Object> result = instance.getFilm("Avengers", searchType.EXACT_NAME);

        assertFalse(result.isEmpty());
        // Comparaison clé par clé
        assertEquals(TEST.get("Title"), result.get("Title"));
        assertEquals(TEST.get("Year"), result.get("Year"));
        assertEquals(TEST.get("Rated"), result.get("Rated"));
        assertEquals(TEST.get("Released"), result.get("Released"));
        assertEquals(TEST.get("Runtime"), result.get("Runtime"));
        assertEquals(TEST.get("Genre"), result.get("Genre"));
        assertEquals(TEST.get("Director"), result.get("Director"));
        assertEquals(TEST.get("Writer"), result.get("Writer"));
        assertEquals(TEST.get("Actors"), result.get("Actors"));
        assertEquals(TEST.get("Plot"), result.get("Plot"));
        assertEquals(TEST.get("Language"), result.get("Language"));
        assertEquals(TEST.get("Country"), result.get("Country"));
        assertEquals(TEST.get("Awards"), result.get("Awards"));
        assertEquals(TEST.get("Poster"), result.get("Poster"));
        assertEquals(TEST.get("Metascore"), result.get("Metascore"));
        assertEquals(TEST.get("imdbRating"), result.get("imdbRating"));
        assertEquals(TEST.get("imdbVotes"), result.get("imdbVotes"));
        assertEquals(TEST.get("imdbID"), result.get("imdbID"));
        assertEquals(TEST.get("Type"), result.get("Type"));
        assertEquals(TEST.get("DVD"), result.get("DVD"));
        assertEquals(TEST.get("BoxOffice"), result.get("BoxOffice"));
        assertEquals(TEST.get("Production"), result.get("Production"));
        assertEquals(TEST.get("Website"), result.get("Website"));
        assertEquals(TEST.get("Response"), result.get("Response"));
    }
}