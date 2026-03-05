package com.arturo.releaseradarapi.services;

import com.arturo.releaseradarapi.dto.TmdbMovieResponse;
import com.arturo.releaseradarapi.entity.Titulo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    @Value("${tmdb.api.key}")
    private String apiKey;

    public Titulo obtenerPeliculaDeTmdb(Long tmdbId){
        String url = apiUrl + tmdbId + "?api_key=" + apiKey + "&language=es-ES";
        try {
        TmdbMovieResponse response = restTemplate.getForObject(url, TmdbMovieResponse.class);

        if (response == null) {
            throw new RuntimeException("No se encontró la película en TMDB");
        }
            return Titulo.builder()
                    .tmdbId(response.getId())
                    .nombre(response.getTitle())
                    .posterUrl("https://image.tmdb.org/t/p/w500" + response.getPosterPath())
                    .fechaEstreno(response.getReleaseDate())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con TMDB: Verifica que el ID sea correcto.");        }
    }
}
