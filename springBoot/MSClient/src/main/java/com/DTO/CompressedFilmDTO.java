package com.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO pour récupérer les CompressedFilm depuis le microservice msFilm
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompressedFilmDTO {
    private String title;
    private int releasetYear;
    private String linkToPoster;
    private String imdbID;
}
