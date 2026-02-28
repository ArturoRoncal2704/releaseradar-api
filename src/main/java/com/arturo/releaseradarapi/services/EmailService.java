package com.arturo.releaseradarapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreoOtp(String destinatario, String codigoOtp){
        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(destinatario);
        mensaje.setSubject("Tu código de verificación - ReleaseRadar");
        mensaje.setText("¡Hola!\n\n"
                + "Bienvenido a ReleaseRadar. Para activar tu cuenta, ingresa el siguiente código de 6 dígitos:\n\n"
                + "👉 " + codigoOtp + " 👈\n\n"
                + "Este código expirará en exactamente 5 minutos por razones de seguridad.\n\n"
                + "Si tú no solicitaste este registro, ignora este correo.\n\n"
                + "El equipo de ReleaseRadar.");

        mailSender.send(mensaje);
        System.out.println("✅ Correo enviado exitosamente a: " + destinatario);

    }
}
