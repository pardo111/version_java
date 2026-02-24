package clinica.version_java.modules.personas.services.interfaces;

import java.util.List;

import clinica.version_java.modules.personas.DTOs.DTOContactosEmergencia;
import clinica.version_java.modules.personas.models.Persona;

public interface ContactosEmergenciaService {

    public void agregarContacto(DTOContactosEmergencia contactosEmergencia) throws  Exception;

    public List<DTOContactosEmergencia> obtenerContactos(int idPaciente);

    public boolean eliminarContactoEmergencia(int idPaciente, int idContacto) throws Exception;

    public void guardarInformacionPersonaRelacionada(List<DTOContactosEmergencia> contactosEmergencia, Persona persona) throws Exception ;
}
