package clinica.version_java.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.CorreoPersona;

@Repository
public interface CorreoPersonaRepository extends JpaRepository<CorreoPersona, Integer>{
    Page<CorreoPersona> findByCorreo(String correo, Pageable pag);
    Page<CorreoPersona> findByCorreoContaining(String correo, Pageable pag);
    boolean existsByCorreo(String correo);
}
