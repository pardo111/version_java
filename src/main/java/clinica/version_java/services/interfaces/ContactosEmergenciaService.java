package clinica.version_java.services.interfaces;

import java.util.List;

import clinica.version_java.DTOs.DTOContactosEmergencia;
import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.models.Persona;

public interface ContactosEmergenciaService {

    public void agregarContacto(DTOContactosEmergencia contactosEmergencia) throws PersonaNotFoundException, Exception;

    public List<DTOContactosEmergencia> obtenerContactos(int idPaciente);

    public boolean eliminarContactoEmergencia(int idPaciente, int idContacto);

    public void guardarInformacionPersonaRelacionada(List<DTOContactosEmergencia> contactosEmergencia, Persona persona) throws Exception ;
}
