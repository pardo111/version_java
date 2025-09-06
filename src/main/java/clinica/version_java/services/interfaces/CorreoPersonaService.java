package clinica.version_java.services.interfaces;

import java.util.List;

import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;

public interface CorreoPersonaService {
    public void guardarCorreos(Persona persona, List<String> correos);

    public boolean agregarCorreo(int idPersona, String correo);

    public boolean actualizarCorreo(String correoNuevo, String correoAntiguo, int idPersona, Estado estado);

}
