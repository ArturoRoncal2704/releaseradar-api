package com.arturo.releaseradarapi.dto;

import com.arturo.releaseradarapi.entity.Titulo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WatchlistResponse {

    private Long id;
    private LocalDateTime fechaAgregado;
    private boolean notificado;

    private Titulo titulo;
}
