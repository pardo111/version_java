package clinica.version_java.usuarios_autenticacion.services;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.enums.Estado;
import clinica.version_java.personas.repositories.PersonaRepository;
import clinica.version_java.usuarios_autenticacion.DTO.DTOUsuarios;
import clinica.version_java.usuarios_autenticacion.models.Usuarios;
import clinica.version_java.usuarios_autenticacion.repositories.UsuariosRepository;
import clinica.version_java.usuarios_autenticacion.services.interfaces.UsuarioService;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImp implements UsuarioService{
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public Usuarios crearUsuarios(DTOUsuarios usuario) {

        Persona persona = personaRepository.findById(usuario.getIdPersona())
                .orElseThrow(() -> new RuntimeException("persona no hallada"));
        Usuarios user = new Usuarios(usuario);
        user.setPersona(persona);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsuario(crearNombreUsuario(persona.getNombres(), persona.getApellidos()));
        user = usuariosRepository.save(user);
        return user;
    }

    @Transactional
    public Usuarios eliminarUsuario(DTOUsuarios usuario) {
        Usuarios user = usuariosRepository.findById(usuario.getId_usuario())
                .orElseThrow(() -> new RuntimeException("persona no hallada"));
        user.setEstado(Estado.INACTIVO);
        usuariosRepository.save(user);
        return user;
    }

    @Transactional
    public Usuarios actualizarUsuario(DTOUsuarios usuario) {
        
        Usuarios user = usuariosRepository.findById(usuario.getId_usuario())
                .orElseThrow(() -> new RuntimeException("persona no hallada"));
        if(user.getEstado()==Estado.ACTIVO){
            user.setUsuario(usuario.getUsuario());
            user.setPuesto(usuario.getPuesto());
            user.setRol(usuario.getRol());
            if(!usuario.getPassword().isEmpty() && usuario.getPassword()!=null){
                user.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            user = usuariosRepository.save(user);

        }
        return user;
    }


    @Override
    public Page<DTOUsuarios> obtenerUsuarios(Pageable pageable){
        Page<Usuarios> usuariosPage = usuariosRepository.findByEstado(Estado.ACTIVO, pageable);
        Page<DTOUsuarios> dtoPage = usuariosPage.map(DTOUsuarios::new);
        return dtoPage;
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
