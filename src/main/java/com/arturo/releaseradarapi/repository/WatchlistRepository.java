package com.arturo.releaseradarapi.repository;

import com.arturo.releaseradarapi.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    List<Watchlist> findByUsuarioId(Long usuarioId);

    boolean existByUsuarioIdAndTituloId(Long usuarioId, Long tiutloId);

    @Query("SELECT w FROM Watchlist w WHERE w.titulo.fechaEstreno = :fechaHoy AND w.notificado = false")
    List<Watchlist> buscarEstrenosParaNotificarHoy(@Param("fechaHoy")LocalDate fechaHoy);

}
