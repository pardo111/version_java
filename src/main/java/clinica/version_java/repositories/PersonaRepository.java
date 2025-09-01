package clinica.version_java.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Sexo;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Persona findByIdPersona(int idPersona);

    Page<Persona> findByDui(String dui, Pageable pageable);

    Optional<Persona> findByDui(String dui);

    boolean existsByDui(String dui);


    @EntityGraph(attributePaths = {"correoPersona", "telefonoPersona"})
    Page<Persona> findByEstado(Estado estado, Pageable pageable );

    @EntityGraph(attributePaths = {"correoPersona", "telefonoPersona"})
    Page<Persona> findByEstadoAndNombresContainingIgnoreCase( Estado estado, String nombre, Pageable pageable  );

    @EntityGraph(attributePaths = {"correoPersona", "telefonoPersona"})
    Page<Persona> findByEstadoAndSexo(Estado estado, Sexo sexo, Pageable pageable);
}
