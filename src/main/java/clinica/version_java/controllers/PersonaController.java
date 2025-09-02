package clinica.version_java.controllers;

import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.DTOs.DTOContactosEmergencia;
import clinica.version_java.DTOs.DTOPersona;
import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.models.CorreoPersona;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            persona = personaService.guardarPersonaCompleta(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(persona);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> actualizar(@RequestBody DTOPersona persona) {
        try {
            System.out.println(persona);
            persona = personaService.guardarPersonaCompleta(persona);
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

    @GetMapping("/obtenerPorDui")
    public Page<DTOPersonaBase> obtenerBusquedaPorDui(
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

    @PostMapping("/eliminarCorreoPersona")
    public CorreoPersona eliminarCorreoPersona(@RequestParam String correo) {
        return personaService.eliminarCorreoPersona( correo);
    }

}
