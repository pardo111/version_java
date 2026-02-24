package clinica.version_java.modules.personas.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.modules.personas.DTOs.DTOContactosEmergencia;
import clinica.version_java.modules.personas.services.interfaces.ContactosEmergenciaService;;

@RestController
@RequestMapping("api/v1/contactoEmergencia")
public class ContactoEmergenciaController {

    @Autowired
    ContactosEmergenciaService contactosEmergenciaService;

    @DeleteMapping("/eliminarContactoEmergencia")
    public ResponseEntity<?> eliminarContactoEmergencia(@RequestBody Map<String, Integer> body) throws Exception {
        if (contactosEmergenciaService.eliminarContactoEmergencia((Integer) body.get("idPaciente"),
                (Integer) body.get("idContacto")))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PostMapping("/agregarContacto")
    public ResponseEntity<?> agregarContacto(@RequestBody DTOContactosEmergencia contacto)
            throws RuntimeException {
        try {
            contactosEmergenciaService.agregarContacto(contacto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();

        }

    }

    @GetMapping("/{id}/contactos")
    public List<DTOContactosEmergencia> obtenerContactos(@PathVariable("id") int id) {
        return contactosEmergenciaService.obtenerContactos(id);
    }

}
