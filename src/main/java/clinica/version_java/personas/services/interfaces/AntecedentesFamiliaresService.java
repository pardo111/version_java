package clinica.version_java.personas.services.interfaces;

import java.util.List;

import clinica.version_java.personas.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.personas.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.personas.models.Persona;

public interface AntecedentesFamiliaresService {
    public List<DTOAntecedentesFamiliares> obtenerAntecedentes(int idPaciente);

    public boolean eliminarAntecedenteFamiliar(int idPaciente, int idFamiliar);

    public void agregarAntecedente(DTOAntecedentesFamiliares antecedentesFamiliares)
            throws PersonaNotFoundException, Exception;

    public void guardarInformacionPersonaRelacionada(Persona persona,
            List<DTOAntecedentesFamiliares> antecedentesFamiliares) throws Exception;
}
