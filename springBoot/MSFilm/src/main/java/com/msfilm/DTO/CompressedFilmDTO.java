package com.msfilm.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompressedFilmDTO {
    private String title;
    private int releasetYear;
    private String linkToPoster;
    private String imdbID;
}
