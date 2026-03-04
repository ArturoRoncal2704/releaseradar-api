package com.arturo.releaseradarapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vip")
public class DemoController {

    @GetMapping("/saludo")
    public ResponseEntity<String> saludoPrivado() {
        return ResponseEntity.ok("¡Hola! Si estás leyendo esto, el portero validó tu Token JWT y te dejó entrar a la zona VIP de ReleaseRadar.");
    }
}