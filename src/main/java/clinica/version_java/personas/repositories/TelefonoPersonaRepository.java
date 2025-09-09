package clinica.version_java.personas.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.TelefonoPersona;

@Repository
public interface TelefonoPersonaRepository extends JpaRepository<TelefonoPersona, Integer>{
    Page<TelefonoPersona> findByTelefono(String telefono, Pageable pag);
    Optional<TelefonoPersona> findByPersonaAndTelefono(Persona persona, String telefono);
    Optional<TelefonoPersona> findByTelefono(String telefono);
    boolean existsByTelefono(String telefono);
}
