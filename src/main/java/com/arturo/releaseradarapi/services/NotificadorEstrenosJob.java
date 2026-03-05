package com.arturo.releaseradarapi.services;

import com.arturo.releaseradarapi.entity.Watchlist;
import com.arturo.releaseradarapi.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificadorEstrenosJob {

    private final WatchlistRepository watchlistRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional
    public void buscarYNotificarEstrenos() {
        LocalDate hoy = LocalDate.now();
        System.out.println("[ReleaseRadar] Buscando estrenos para hoy: " + hoy);

        List<Watchlist> pendientes = watchlistRepository.buscarEstrenosParaNotificarHoy(hoy);

        if(pendientes.isEmpty()){
            System.out.println("No hay estrenos pendientes de notificar en este momento.");
            return;
        }

        for(Watchlist item : pendientes){
            String correoDestino = item.getUsuario().getEmail();
            String nombreTitulo = item.getTitulo().getNombre();

            String asunto = "¡Hoy se estrena " + nombreTitulo + "! 🍿";
            String mensaje = "Hola,\n\n¡El día ha llegado! Hoy es el estreno oficial de "
                    + nombreTitulo + ".\n\nPrepara tu lugar favorito y a disfrutar.\n\nAtentamente,\nTu Radar de Lanzamientos 🎬";

            try{
                emailService.enviarCorreo(correoDestino, asunto, mensaje);                item.setNotificado(true);
                watchlistRepository.save(item);
                System.out.println("¡Éxito! Correo enviado a " + correoDestino + " sobre " + nombreTitulo);
            } catch (Exception e) {
                System.err.println("Error enviando correo a " + correoDestino + ": " + e.getMessage());            }
        }
    }
}
