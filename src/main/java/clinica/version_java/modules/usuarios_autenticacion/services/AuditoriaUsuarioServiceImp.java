package clinica.version_java.modules.usuarios_autenticacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.version_java.modules.usuarios_autenticacion.models.AuditoriaUsuario;
import clinica.version_java.modules.usuarios_autenticacion.models.enums.EventoUsuario;
import clinica.version_java.modules.usuarios_autenticacion.repositories.AuditoriaUsuarioRepository;
import clinica.version_java.modules.usuarios_autenticacion.services.interfaces.AudiroriaUsuarioService;

@Service
public class AuditoriaUsuarioServiceImp implements AudiroriaUsuarioService {

    @Autowired
    AuditoriaUsuarioRepository auditoriaUsuarioRepository;

    @Override
    public void registrarAuditoria(String ip, EventoUsuario evento, int usuario) {
        auditoriaUsuarioRepository.save(
                new AuditoriaUsuario(
                        ip,
                        evento,
                        usuario));
    }

}
