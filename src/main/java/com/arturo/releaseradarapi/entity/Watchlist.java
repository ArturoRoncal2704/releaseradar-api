package com.arturo.releaseradarapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_watchlist", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id","titulo_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titutlo_id",nullable = false)
    private Titulo titulo;

    @Column(nullable = false)
    private Boolean notificado;

    @Column(name = "fecha_agregado",updatable = false)
    private LocalDateTime fechaAgregado;

    @PrePersist
    protected  void OnCreate(){
        this.fechaAgregado = LocalDateTime.now();
                if(this.notificado == null){
                    this.notificado = false;
                }
    }
}
