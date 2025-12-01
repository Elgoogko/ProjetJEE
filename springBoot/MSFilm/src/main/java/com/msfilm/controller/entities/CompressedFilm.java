package com.msfilm.controller.entities;

import com.actors.Actor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class CompressedFilm extends Actor {
    
    @NotBlank
    @NotEmpty
    @NotNull
    private String title;

    @Min(0)
    private int releasetYear;
    
    @NotBlank
    @NotEmpty
    @NotNull
    private String linkToPoster;
    
    @NotBlank
    @NotEmpty
    @NotNull
    private String imdbID;
}
