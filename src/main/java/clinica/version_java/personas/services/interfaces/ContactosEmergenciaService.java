package clinica.version_java.personas.services.interfaces;

import java.util.List;

import clinica.version_java.personas.DTOs.DTOContactosEmergencia;
import clinica.version_java.personas.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.personas.models.Persona;

public interface ContactosEmergenciaService {

    public void agregarContacto(DTOContactosEmergencia contactosEmergencia) throws PersonaNotFoundException, Exception;

    public List<DTOContactosEmergencia> obtenerContactos(int idPaciente);

    public boolean eliminarContactoEmergencia(int idPaciente, int idContacto);

    public void guardarInformacionPersonaRelacionada(List<DTOContactosEmergencia> contactosEmergencia, Persona persona) throws Exception ;
}
