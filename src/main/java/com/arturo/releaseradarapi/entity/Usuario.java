package com.arturo.releaseradarapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false, length = 100)
    private String email;

    @Column(nullable = false,length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "codigo_otp",length = 6)
    private String codigoOtp;

    @Column(name = "fecha_expiracion_otp")
    private LocalDateTime fechaExpiracionOtp;

    @Column(name = "fecha_creacioon",updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null){
            this.estado = "INACTIVO";
        }
    }
}
