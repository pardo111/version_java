package clinica.version_java.personas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clinica.version_java.personas.models.AntecedentesFamiliares;
import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.enums.Estado;

@Repository
public interface AntecedentesFamiliaresRepository extends JpaRepository<AntecedentesFamiliares, Integer> {
    @Query("""
            SELECT af
            FROM AntecedentesFamiliares af
            JOIN FETCH af.familiar f
            WHERE af.paciente.idPersona = :idPaciente
              AND af.estado = clinica.version_java.models.enums.Estado.ACTIVO
              AND f.estado = clinica.version_java.models.enums.Estado.ACTIVO
            """)
    List<AntecedentesFamiliares> findContactosByPacienteId(int idPaciente);

    boolean existsByPacienteAndFamiliarAndEstado (Persona paciente, Persona familiar, Estado estado);

    Optional<AntecedentesFamiliares> findByPacienteAndFamiliar(Persona paciente, Persona familiar);
}
