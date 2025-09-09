package clinica.version_java.personas.services.interfaces;

import java.util.List;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.enums.Estado;

public interface CorreoPersonaService {
    public void guardarCorreos(Persona persona, List<String> correos);

    public boolean agregarCorreo(int idPersona, String correo);

    public boolean actualizarCorreo(String correoNuevo, String correoAntiguo, int idPersona, Estado estado);

}
