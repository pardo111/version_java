package clinica.version_java.personas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.personas.models.AntecedentesFamiliares;
import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.enums.Estado;

@Repository
public interface AntecedentesFamiliaresRepository extends JpaRepository<AntecedentesFamiliares, Integer> {
    List<AntecedentesFamiliares> findByPacienteIdPersonaAndEstadoAndFamiliarEstado(
        int idPaciente, Estado estadoPaciente, Estado estadoFamiliar);

 

  boolean existsByPacienteAndFamiliarAndEstado(Persona paciente, Persona familiar, Estado estado);

  Optional<AntecedentesFamiliares> findByPacienteAndFamiliar(Persona paciente, Persona familiar);
}
