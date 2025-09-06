package clinica.version_java.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.models.enums.Estado;
import clinica.version_java.services.CorreoPersonaService;

@RestController
@RequestMapping("/api/v1/correo")
public class CorreoPersonaController {

    @Autowired
    CorreoPersonaService correoPersonaService;

    @PutMapping("/actualizarCorreo")
    public ResponseEntity<?> actualizarCorreo(@RequestBody Map<String, String> body) {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String correoNuevo = body.get("correoNuevo");
        String correoAntiguo = body.get("correoAntiguo");
        Estado estado = Estado.valueOf(body.get("estado"));

        if (correoPersonaService.actualizarCorreo(correoNuevo, correoAntiguo, idPersona, estado))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @DeleteMapping("/borrarCorreo")
    public ResponseEntity<?> borrarCorreo(@RequestBody Map<String, String> body) {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String correoAntiguo = body.get("correoAntiguo");

        if (correoPersonaService.actualizarCorreo(correoAntiguo, correoAntiguo, idPersona, Estado.INACTIVO))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PostMapping("/agregarCorreo")
    public ResponseEntity<?> agregarCorreo(@RequestBody Map<String, String> body) {
        Integer idPersona = Integer.parseInt(body.get("idPersona"));
        String correo = body.get("correo");
        if (correoPersonaService.agregarCorreo(idPersona, correo))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
