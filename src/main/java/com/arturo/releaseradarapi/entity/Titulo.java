package com.arturo.releaseradarapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_titulos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tmdb_id" , unique = true, nullable = false)
    private Long tmdbId;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(name = "poster_url",length = 255)
    private String posterUrl;

    @Column(name = "fecha_estreno", nullable = false)
    private LocalDate fechaEstreno;
}
