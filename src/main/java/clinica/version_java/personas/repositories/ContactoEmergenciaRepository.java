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
public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Integer> {

  List<ContactoEmergencia> findByPacienteIdPersonaAndEstadoAndContactoEstado(int idPaciente, Estado estadoCe,
      Estado estadoContacto);

  boolean existsByPacienteAndContactoAndEstado(Persona paciente, Persona contacto, Estado estado);

  @Query("SELECT ce FROM ContactoEmergencia ce WHERE ce.contacto.idPersona = :idContacto AND ce.paciente.idPersona = :idPaciente")
  Optional<ContactoEmergencia> findByContactoAndPaciente(int idContacto, int idPaciente);

}
