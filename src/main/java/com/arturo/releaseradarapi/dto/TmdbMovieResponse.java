package com.arturo.releaseradarapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TmdbMovieResponse {

    private Long id;

    private String title;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

}
