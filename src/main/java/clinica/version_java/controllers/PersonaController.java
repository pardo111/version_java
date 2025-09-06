package clinica.version_java.controllers;

import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.DTOs.DTOContactosEmergencia;
import clinica.version_java.DTOs.DTOPersona;
import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Sexo;
import clinica.version_java.models.enums.TipoPersona;
import clinica.version_java.services.PersonaService;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/persona")
public class PersonaController {

    PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> crearPersonaCompleta(@RequestBody DTOPersona persona) {
        try {
            System.out.println(persona);
            persona = personaService.guardarOActualizarPersonaCompleta(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(persona);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> actualizar(@RequestBody DTOPersona persona) {
        try {
            persona = personaService.guardarOActualizarPersonaCompleta(persona);
            return ResponseEntity.status(HttpStatus.OK).body(persona);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody DTOPersona persona) {
        try {
            persona = personaService.guardarOActualizarPersonaCompleta(persona);
            return ResponseEntity.status(HttpStatus.OK).body(persona);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/pages")
    public Page<DTOPersonaBase> obtenerPersonas(
            @PageableDefault(page = 0, size = 10, sort = "nombres", direction = Sort.Direction.ASC) Pageable pageable,
            TipoPersona tipoPersona) {
        return personaService.obtenerPersonas(pageable, tipoPersona);
    }

    @GetMapping("/obtenerPorNombre")
    public Page<DTOPersonaBase> obtenerBusquedaSimilarNombre(
            Pageable pageable,
            @RequestParam String nombre) {
        return personaService.obtenerBusquedaSimilarNombre(pageable, nombre);
    }

    @GetMapping("/obtenerBusquedaPorDuiExacto")
    public Page<DTOPersonaBase> obtenerBusquedaPorDuiExacto(
            Pageable pageable,
            @RequestParam String dui) {
        return personaService.obtenerPorDui(pageable, dui);
    }

    @GetMapping("/obtenerPorSexo")
    public Page<DTOPersonaBase> obtenerPorSexo(
            Pageable pageable,
            @RequestParam Sexo sexo) {
        return personaService.obtenerPorSexo(pageable, sexo);
    }

    @GetMapping("/{id}/contactos")
    public List<DTOContactosEmergencia> obtenerContactos(@PathVariable("id") int id) {
        return personaService.obtenerContactos(id);
    }

    @GetMapping("/{id}/antecedentes")
    public List<DTOAntecedentesFamiliares> obtenerAntecedentes(@PathVariable("id") int id) {
        return personaService.obtenerAntecedentes(id);
    }

    @DeleteMapping("/eliminarContactoEmergencia")
    public ResponseEntity<?> eliminarContactoEmergencia(@RequestBody Map<String, Integer> body) {
        if (personaService.eliminarContactoEmergencia((Integer) body.get("idPaciente"),
                (Integer) body.get("idContacto")))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @DeleteMapping("/eliminarAntecedenteFamiliar")
    public ResponseEntity<?> eliminarAntecedenteFamiliar(@RequestBody Map<String, Integer> body) {
        if (personaService.eliminarAntecedenteFamiliar((Integer) body.get("idPaciente"),
                (Integer) body.get("idAntecedente")))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PutMapping("/actualizarCorreo")
    public ResponseEntity<?> actualizarCorreo(@RequestBody Map<String, String> body) {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String correoNuevo = body.get("correoNuevo");
        String correoAntiguo = body.get("correoAntiguo");
        Estado estado = Estado.valueOf(body.get("estado"));

        if (personaService.actualizarCorreo(correoNuevo, correoAntiguo, idPersona, estado))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PutMapping("/actualizarTelefono")
    public ResponseEntity<?> actualizarTelefono(@RequestBody Map<String, String> body) {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String telefonoNuevo = body.get("telefonoNuevo");
        String telefonoViejo = body.get("telefonoViejo");
        Estado estado = Estado.valueOf(body.get("estado"));

        if (personaService.actualizarTelefono(telefonoNuevo, telefonoViejo, idPersona, estado))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @DeleteMapping("/borrarCorreo")
    public ResponseEntity<?> borrarCorreo(@RequestBody Map<String, String> body) {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String correoAntiguo = body.get("correoAntiguo");

        if (personaService.actualizarCorreo(correoAntiguo, correoAntiguo, idPersona, Estado.INACTIVO))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @DeleteMapping("/borrarTelefono")
    public ResponseEntity<?> borrarTelefono(@RequestBody Map<String, String> body) {
        int idPersona = Integer.parseInt(body.get("idPersona"));
        String telefonoViejo = body.get("telefonoViejo");
        if (personaService.actualizarTelefono(telefonoViejo, telefonoViejo, idPersona, Estado.INACTIVO))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PostMapping("/agregarCorreo")
    public ResponseEntity<?> agregarCorreo(@RequestBody Map<String, String> body) {
        Integer idPersona = Integer.parseInt(body.get("idPersona"));
        String correo = body.get("correo");
        if (personaService.agregarCorreo(idPersona, correo))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PostMapping("/agregarTelefono")
    public ResponseEntity<?> agregarTelefono(@RequestBody Map<String, String> body) {
        Integer idPersona = Integer.parseInt(body.get("idPersona"));
        String telefono = body.get("telefono");
        if (personaService.agregarTelefono(idPersona, telefono))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @PostMapping("/agregarAntecedente")
    public ResponseEntity<?> agregarAntecedente(@RequestBody DTOAntecedentesFamiliares antecedente)
            throws RuntimeException {
        try {
            personaService.agregarAntecedente(antecedente);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @PostMapping("/agregarContacto")
    public ResponseEntity<?> agregarContacto(@RequestBody DTOContactosEmergencia contacto)
            throws RuntimeException {
        try {
            personaService.agregarContacto(contacto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();

        }

    }

}
