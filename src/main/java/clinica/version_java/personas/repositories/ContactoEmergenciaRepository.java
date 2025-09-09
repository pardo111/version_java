package clinica.version_java.personas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clinica.version_java.personas.models.ContactoEmergencia;
import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.enums.Estado;

@Repository
public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Integer>{
        @Query("""
        SELECT ce 
        FROM ContactoEmergencia ce
        JOIN FETCH ce.contacto c
        WHERE ce.paciente.idPersona = :idPaciente
          AND ce.estado = clinica.version_java.models.enums.Estado.ACTIVO
          AND c.estado = clinica.version_java.models.enums.Estado.ACTIVO
        """)
    List<ContactoEmergencia> findContactosByPacienteId(int idPaciente);

    boolean existsByPacienteAndContactoAndEstado(Persona paciente, Persona contacto, Estado estado);


    @Query("SELECT ce FROM ContactoEmergencia ce WHERE ce.contacto.idPersona = :idContacto AND ce.paciente.idPersona = :idPaciente")
    Optional<ContactoEmergencia> findByContactoAndPaciente(int idContacto, int idPaciente);

}
