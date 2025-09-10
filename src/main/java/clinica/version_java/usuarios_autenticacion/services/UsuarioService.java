package clinica.version_java.usuarios_autenticacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.repositories.PersonaRepository;
import clinica.version_java.usuarios_autenticacion.DTO.DTOUsuarios;
import clinica.version_java.usuarios_autenticacion.models.Usuarios;
import clinica.version_java.usuarios_autenticacion.repositories.UsuariosRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonaRepository personaRepository;

    public Usuarios crearUsuarios(DTOUsuarios usuario) {

        Persona persona = personaRepository.findById(usuario.getIdPersona())
                .orElseThrow(() -> new RuntimeException("persona no hallada"));
        Usuarios user = new Usuarios(usuario);
        user.setPersona(persona);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsuario(crearNombreUsuario(persona.getNombres(), persona.getApellidos()));
        user= usuariosRepository.save(user);
        return user;
    }

    private String crearNombreUsuario(String nombre, String apellido) {
        if (nombre.isEmpty() || nombre == null)
            return "";

        int indiceNombre = nombre.indexOf(" ");
        int indiceApellido = apellido.indexOf(" ");

        apellido = apellido.substring(0, indiceApellido);
        nombre = nombre.substring(0, indiceNombre);
        apellido = capitalizar(apellido);
        nombre = capitalizar(nombre);

        String usuario = nombre + "_" + apellido;

        return usuario;
    }

    private String capitalizar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
