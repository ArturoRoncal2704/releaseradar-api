package com.arturo.releaseradarapi.services;

import com.arturo.releaseradarapi.dto.WatchlistResponse;
import com.arturo.releaseradarapi.entity.Titulo;
import com.arturo.releaseradarapi.entity.Watchlist;

import java.util.List;

public interface WatchlistService {

    Watchlist agregarTitulo(String emailUsuario, Titulo tituloData);

    List<WatchlistResponse> obtenerMiWatchlist(String emailUsuario);

    void eliminarTituloDeWatchlist(Long watchlistId, String emailUsuario);
}
