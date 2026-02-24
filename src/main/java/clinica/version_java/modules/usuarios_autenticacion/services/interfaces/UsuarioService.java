package clinica.version_java.modules.usuarios_autenticacion.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import clinica.version_java.modules.usuarios_autenticacion.DTO.DTOUsuarios;
import clinica.version_java.modules.usuarios_autenticacion.models.Usuarios;

public interface UsuarioService {

    public Usuarios crearUsuarios(DTOUsuarios usuario);

    public Usuarios eliminarUsuario(DTOUsuarios usuario);

    public Usuarios actualizarUsuario(DTOUsuarios usuario);

    public Page<DTOUsuarios> obtenerUsuarios(Pageable pageable);
}
