package clinica.version_java.personas.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.personas.models.CorreoPersona;
import clinica.version_java.personas.models.Persona;

@Repository
public interface CorreoPersonaRepository extends JpaRepository<CorreoPersona, Integer>{
    Page<CorreoPersona> findByCorreo(String correo, Pageable pag);
    Optional<CorreoPersona> findByCorreo(String correo );
    boolean existsByCorreo(String correo);
    Optional<CorreoPersona> findByPersonaAndCorreo(Persona idPersona, String correo);
}
