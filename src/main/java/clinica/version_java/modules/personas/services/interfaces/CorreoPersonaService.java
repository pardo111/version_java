package clinica.version_java.modules.personas.services.interfaces;

import java.util.List;

import clinica.version_java.modules.personas.models.Persona;
import clinica.version_java.modules.personas.models.enums.Estado;

public interface CorreoPersonaService {
    public void guardarCorreos(Persona persona, List<String> correos) throws Exception;

    public boolean agregarCorreo(int idPersona, String correo) throws Exception;

    public boolean actualizarCorreo(String correoNuevo, String correoAntiguo, int idPersona, Estado estado) throws Exception;

}
