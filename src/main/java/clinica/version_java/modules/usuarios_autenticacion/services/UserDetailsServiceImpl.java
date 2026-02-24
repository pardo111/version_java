package clinica.version_java.modules.usuarios_autenticacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import clinica.version_java.modules.personas.models.enums.Estado;
import clinica.version_java.modules.usuarios_autenticacion.models.Usuarios;
import clinica.version_java.modules.usuarios_autenticacion.repositories.UsuariosRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuarios usuario = usuariosRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado" + username));

        return User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getPassword())
                .roles(usuario.getRol().name())
                .disabled(usuario.getEstado() != null && usuario.getEstado() != Estado.ACTIVO)
                .build();
    }
}
