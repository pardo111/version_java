package clinica.version_java.modules.usuarios_autenticacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.modules.usuarios_autenticacion.models.AuditoriaUsuario;

@Repository
public interface AuditoriaUsuarioRepository extends JpaRepository<AuditoriaUsuario, Integer>{
    
}
