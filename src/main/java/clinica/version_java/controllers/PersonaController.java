package clinica.version_java.controllers;

import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.DTOs.DTOPersona;
import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.models.Persona;
import clinica.version_java.services.PersonaService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @GetMapping
    public Page<DTOPersonaBase> obtenerPersonas(
                @PageableDefault(page = 0, size = 10, sort = "nombres", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return personaService.obtenerPersonas(pageable);
    }
}
