package clinica.version_java.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.AntecedentesFamiliares;
import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;

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
}
