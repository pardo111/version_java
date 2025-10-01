package clinica.version_java.usuarios_autenticacion.services.interfaces;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import clinica.version_java.usuarios_autenticacion.DTO.DTOUsuarios;
import clinica.version_java.usuarios_autenticacion.models.Usuarios;

public interface UsuarioService {

    public Usuarios crearUsuarios(DTOUsuarios usuario);

    public Usuarios eliminarUsuario(DTOUsuarios usuario);

    public Usuarios actualizarUsuario(DTOUsuarios usuario);

    public Page<DTOUsuarios> obtenerUsuarios(Pageable pageable);
}
