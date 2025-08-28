package clinica.version_java.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Sexo;
import clinica.version_java.models.enums.TipoPersona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Page<Persona> findByApellidos(String apellidos, Pageable pageable);

    Persona findByIdPersona(int idPersona);

    Page<Persona> findByApellidosContaining(String apellidos, Pageable pageable);

    Page<Persona> findByNombres(String nombres, Pageable pageable);

    Page<Persona> findByNombresContaining(String nombres, Pageable pageable);

    Page<Persona> findByDui(String dui, Pageable pageable);

    Optional<Persona> findByDui(String dui);

    boolean existsByDui(String dui);

    Page<Persona> findByDuiContaining(String dui, Pageable pageable);

    Page<Persona> findBySexo(Sexo sexo, Pageable pageable);

    Page<Persona> findByTipoPersona(TipoPersona tipoPersona, Pageable pageable);

}
