package com.arturo.releaseradarapi.services.impl;

import com.arturo.releaseradarapi.dto.WatchlistResponse;
import com.arturo.releaseradarapi.entity.Titulo;
import com.arturo.releaseradarapi.entity.Usuario;
import com.arturo.releaseradarapi.entity.Watchlist;
import com.arturo.releaseradarapi.repository.TituloRepository;
import com.arturo.releaseradarapi.repository.UsuarioRepository;
import com.arturo.releaseradarapi.repository.WatchlistRepository;
import com.arturo.releaseradarapi.services.TmdbService;
import com.arturo.releaseradarapi.services.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final TituloRepository tituloRepository;
    private final UsuarioRepository usuarioRepository;
    private final TmdbService tmdbService;

    @Override
    public Watchlist agregarTitulo(String emailUsuario, Titulo tituloData) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Titulo titulo = tituloRepository.findByTmdbId(tituloData.getTmdbId())
                .orElseGet(() -> {
                        System.out.println("🌐 Descargando datos de TMDB para el ID: " + tituloData.getTmdbId());
                        Titulo tituloOficial = tmdbService.obtenerPeliculaDeTmdb(tituloData.getTmdbId());
                        return tituloRepository.save(tituloOficial);

                    });



        if(watchlistRepository.existsByUsuarioIdAndTituloId(usuario.getId(), titulo.getId())){
            throw new RuntimeException("Este titulo ya está en tu WatchList");
        }

        Watchlist nuevaEntrada = Watchlist.builder()
                .usuario(usuario)
                .titulo(titulo)
                .build();
        return watchlistRepository.save(nuevaEntrada);
    }

    @Override
    public List<WatchlistResponse> obtenerMiWatchlist(String emailUsuario) {

        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Watchlist> miLista = watchlistRepository.findByUsuarioId(usuario.getId());

        return miLista.stream().map(item -> WatchlistResponse.builder()
                .id(item.getId())
                .fechaAgregado(item.getFechaAgregado())
                .notificado(item.getNotificado())
                .titulo(item.getTitulo())
                .build()
        ).toList();
    }

    @Override
    public void eliminarTituloDeWatchlist(Long watchlistId, String emailUsuario) {

        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Watchlist item = watchlistRepository.findById(watchlistId)
                .orElseThrow(() -> new RuntimeException("El registro no existe en la Watchlist"));

        if (!item.getUsuario().getId().equals(usuario.getId())){
            throw new RuntimeException("No tienes permiso para eliminar este título");
        }
        watchlistRepository.delete(item);
    }
}
