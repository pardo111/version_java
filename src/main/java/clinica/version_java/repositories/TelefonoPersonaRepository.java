package clinica.version_java.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.TelefonoPersona;

@Repository
public interface TelefonoPersonaRepository extends JpaRepository<TelefonoPersona, Integer>{
    Page<TelefonoPersona> findByTelefono(String telefono, Pageable pag);
    Optional<TelefonoPersona> findByTelefono(String telefono);
    boolean existsByTelefono(String telefono);
}
