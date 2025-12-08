package com.msfilm.controller.entities;

import org.json.JSONObject;

import com.actors.Actor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "CompressedFilm [title=" + title + ", releasetYear=" + releasetYear + ", linkToPoster=" + linkToPoster
                + ", imdbID=" + imdbID + "]";
    }

    @Override
    public void receive(JSONObject message, String senderID) {
        throw new UnsupportedOperationException("Unimplemented method 'receive'");
    }

    @Override
    public JSONObject castToJSONObject() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'castToJSONObject'");
    }
}
