package clinica.version_java.usuarios_autenticacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica.version_java.usuarios_autenticacion.models.Usuarios;
import clinica.version_java.usuarios_autenticacion.repositories.UsuariosRepository;

@Service
public class UsuarioService {
    @Autowired
    private  UsuariosRepository usuariosRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Usuarios crearUsuarios(Usuarios usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuariosRepository.save(usuario);
    } 

    



}
