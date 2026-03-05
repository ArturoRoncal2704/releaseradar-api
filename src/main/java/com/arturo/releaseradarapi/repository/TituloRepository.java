package com.arturo.releaseradarapi.repository;

import com.arturo.releaseradarapi.entity.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
    Optional<Titulo> findByTmdbId(Long tmdbId);
}
