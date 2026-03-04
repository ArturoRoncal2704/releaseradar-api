package com.arturo.releaseradarapi.services;

import com.arturo.releaseradarapi.entity.Usuario;
import com.arturo.releaseradarapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Transactional
    public String registrarUsuario(String email, String password){
        var usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()){
            Usuario usuarioExistente = usuarioOpt.get();
            if("ACTIVO".equals(usuarioExistente.getEstado())){
                throw new RuntimeException("Error: El correo ya está registrado en ReleaseRadar.");
            }else {
                String nuevoOtp = generarOtp();
                usuarioExistente.setPassword(passwordEncoder.encode(password));
                usuarioExistente.setCodigoOtp(nuevoOtp);
                usuarioExistente.setFechaExpiracionOtp(LocalDateTime.now().plusMinutes(5));

                usuarioRepository.save(usuarioExistente);
                emailService.enviarCorreoOtp(email, nuevoOtp);

                return "Parece que no terminaste tu registro anterior. Te hemos enviado un nuevo código";
            }
        }

        String codigoOtp = generarOtp();
        Usuario nuevoUsuario = Usuario.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .codigoOtp(codigoOtp)
                .fechaExpiracionOtp(LocalDateTime.now().plusMinutes(5))
                .build();
        usuarioRepository.save(nuevoUsuario);
        emailService.enviarCorreoOtp(email, codigoOtp);
        return "Usuario registrado correctamente. Revisa tu bandeja de entrada para activar tu cuenta.";
    }

    @Transactional
    public String reenviarCodigoOtp(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado."));
        if("ACTIVO".equals(usuario.getEstado())){
            throw new RuntimeException(("Tu cuenta ya está activa. Puedes iniciar sesión directamente."));
        }

        String nuevoOtp = generarOtp();
        usuario.setCodigoOtp(nuevoOtp);
        usuario.setFechaExpiracionOtp(LocalDateTime.now().plusMinutes(5));

        usuarioRepository.save(usuario);
        emailService.enviarCorreoOtp(email,nuevoOtp);

        return "Se ha reenviado un nuevo código de 6 dígitos a tu correo.";
    }

    @Transactional
    public String verificarUsuario (String email, String codigoOtp){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado."));

        if("ACTIVO".equals(usuario.getEstado())){
            throw new RuntimeException("La cuenta ya se encuentra activada");
        }
        if (!codigoOtp.equals(usuario.getCodigoOtp())){
            throw new RuntimeException("Error: Código OTP incorrecto.");
        }
        if(usuario.getFechaExpiracionOtp().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Error: El código ha expirado. Debes solicitar uno nuevo.");
        }

        usuario.setEstado("ACTIVO");
        usuario.setCodigoOtp(null);
        usuario.setFechaExpiracionOtp(null);

        usuarioRepository.save(usuario);
        return "¡Cuenta activada con éxito! Ya puedes iniciar sesión";
    }

    public String loginUsuario(String email, String password){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: Credenciales incorrectas."));

        if(!"ACTIVO".equals(usuario.getEstado())){
            throw new RuntimeException("Error: Tu cuenta no está activa. Revisa tu correo o solicita un nuevo código");
        }

        if(!passwordEncoder.matches(password, usuario.getPassword())){
            throw new RuntimeException("Error: Credenciales incorrectas.");
        }

        return jwtService.generartoken(email);
    }


    private String generarOtp(){
        Random random = new Random();
        int numero = 100000 + random.nextInt(900000);
        return String.valueOf(numero);
    }


}
