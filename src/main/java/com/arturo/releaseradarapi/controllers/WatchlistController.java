package com.arturo.releaseradarapi.controllers;

import com.arturo.releaseradarapi.dto.WatchlistResponse;
import com.arturo.releaseradarapi.entity.Titulo;
import com.arturo.releaseradarapi.entity.Watchlist;
import com.arturo.releaseradarapi.services.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<?> agregarAWatchlist(@RequestBody Titulo tituloData, Principal principal){
        try {
            String emailUsuario = principal.getName();
            Watchlist nuevaEntrada = watchlistService.agregarTitulo(emailUsuario, tituloData);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEntrada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<WatchlistResponse>> verMiWatchlist( Principal principal){
        String emailUsuario = principal.getName();

        List<WatchlistResponse> miLista = watchlistService.obtenerMiWatchlist(emailUsuario);

        return ResponseEntity.ok(miLista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDeMiWatchlist(@PathVariable Long id, Principal principal) {
        String emailUsuario = principal.getName();

        watchlistService.eliminarTituloDeWatchlist(id, emailUsuario);

        return ResponseEntity.ok("Título eliminado correctamente de tu Watchlist");
    }
}
