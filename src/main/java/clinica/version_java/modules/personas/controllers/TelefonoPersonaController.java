package clinica.version_java.modules.personas.controllers;

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

import clinica.version_java.modules.personas.models.enums.Estado;
import clinica.version_java.modules.personas.services.interfaces.TelefonoPersonaService;

@RestController
@RequestMapping("/api/v1/telefono")
public class TelefonoPersonaController {
    @Autowired
    TelefonoPersonaService telefonoPersonaService;

    @PutMapping("/actualizarTelefono")
    public ResponseEntity<?> actualizarTelefono(@RequestBody Map<String, String> body) throws Exception {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String telefonoNuevo = body.get("telefonoNuevo");
        String telefonoViejo = body.get("telefonoViejo");
        Estado estado = Estado.valueOf(body.get("estado"));

        if (telefonoPersonaService.actualizarTelefono(telefonoNuevo, telefonoViejo, idPersona, estado))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PostMapping("/agregarTelefono")
    public ResponseEntity<?> agregarTelefono(@RequestBody Map<String, String> body) throws Exception {
        Integer idPersona = Integer.parseInt(body.get("idPersona"));
        String telefono = body.get("telefono");
        if (telefonoPersonaService.agregarTelefono(idPersona, telefono))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @DeleteMapping("/borrarTelefono")
    public ResponseEntity<?> borrarTelefono(@RequestBody Map<String, String> body) throws Exception {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String telefonoViejo = body.get("telefonoViejo");
        if (telefonoPersonaService.actualizarTelefono(telefonoViejo, telefonoViejo, idPersona, Estado.INACTIVO))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
