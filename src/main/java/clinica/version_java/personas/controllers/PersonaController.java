package clinica.version_java.personas.controllers;

import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.personas.DTOs.DTOPersona;
import clinica.version_java.personas.DTOs.DTOPersonaBase;
import clinica.version_java.personas.models.enums.Sexo;
import clinica.version_java.personas.models.enums.TipoPersona;
import clinica.version_java.personas.services.interfaces.PersonaService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

 
}
