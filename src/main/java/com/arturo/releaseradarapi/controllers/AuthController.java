package com.arturo.releaseradarapi.controllers;

import com.arturo.releaseradarapi.dto.ReenviarOtpRequest;
import com.arturo.releaseradarapi.dto.RegistroRequest;
import com.arturo.releaseradarapi.dto.VerificarOtpRequest;
import com.arturo.releaseradarapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody RegistroRequest request){
        try{
            String mensaje = authService.registrarUsuario(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(mensaje);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verificar")
    public ResponseEntity<String> verificar(@RequestBody VerificarOtpRequest request){
        try{
            String mensaje = authService.verificarUsuario(request.getEmail(), request.getCodigoOtp());
            return ResponseEntity.ok(mensaje);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reenviar-codigo")
    public ResponseEntity<String> reenviarCodigo(@RequestBody ReenviarOtpRequest request) {
        try {
            String mensaje = authService.reenviarCodigoOtp(request.getEmail());
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
