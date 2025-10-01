package clinica.version_java.usuarios_autenticacion.controllers;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.repositories.PersonaRepository;
import clinica.version_java.usuarios_autenticacion.DTO.DTOUsuarios;
import clinica.version_java.usuarios_autenticacion.repositories.UsuariosRepository;
import clinica.version_java.usuarios_autenticacion.services.interfaces.UsuarioService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    @PutMapping("/update")
    public ResponseEntity<?> editarUsuario(@RequestBody DTOUsuarios usuario) {
        try {
            DTOUsuarios user = new DTOUsuarios(
                    usuarioService.actualizarUsuario(
                            usuario));
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/delete")
    public ResponseEntity<?> eliminarUsuario(@RequestBody DTOUsuarios usuario) {
        try {
            usuarioService.eliminarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> paginar(@PageableDefault(
            size = 10, page = 0, sort = "id_usuario"
    ) Pageable pageable ) {
        try {
            Page<DTOUsuarios> usuarios = usuarioService.obtenerUsuarios(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
