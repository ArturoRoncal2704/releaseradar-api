package com.arturo.releaseradarapi.entity;

import jakarta.persistence.*;
import lombok.*;

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

    
}
