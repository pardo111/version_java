package clinica.version_java.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.ContactoEmergencia;
import clinica.version_java.models.Persona;

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

    boolean existsByPacienteAndContacto(Persona paciente, Persona contacto);

}
