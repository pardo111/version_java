package clinica.version_java.services.interfaces;

import java.util.List;

import clinica.version_java.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.models.Persona;

public interface AntecedentesFamiliaresService {
    public List<DTOAntecedentesFamiliares> obtenerAntecedentes(int idPaciente);

    public boolean eliminarAntecedenteFamiliar(int idPaciente, int idFamiliar);

    public void agregarAntecedente(DTOAntecedentesFamiliares antecedentesFamiliares)
            throws PersonaNotFoundException, Exception;

    public void guardarInformacionPersonaRelacionada(Persona persona,
            List<DTOAntecedentesFamiliares> antecedentesFamiliares) throws Exception;
}
