package clinica.version_java.modules.usuarios_autenticacion.services.interfaces;

import clinica.version_java.modules.usuarios_autenticacion.models.enums.EventoUsuario;

public interface AudiroriaUsuarioService {

    public void registrarAuditoria(String ip, EventoUsuario evento, int usuario);
    
}
