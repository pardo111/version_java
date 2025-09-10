package clinica.version_java.usuarios_autenticacion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.repositories.PersonaRepository;
import clinica.version_java.usuarios_autenticacion.DTO.DTOUsuarios;
import clinica.version_java.usuarios_autenticacion.repositories.UsuariosRepository;
import clinica.version_java.usuarios_autenticacion.services.UsuarioService;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    PersonaRepository personaRepository;

    @PostMapping("/create")
    public ResponseEntity<?> crearUsuario(@RequestBody DTOUsuarios usuario) {
        try {
            Persona persona = personaRepository.findById(usuario.getIdPersona())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

            if (usuariosRepository.existsByPersona(persona))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este usuario ya existe para la persona: "
                        + persona.getNombres() + " " + persona.getApellidos());

            DTOUsuarios user = new DTOUsuarios(usuarioService.crearUsuarios(usuario));
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
