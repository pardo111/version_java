package clinica.version_java.modules.personas.services.interfaces;

import java.util.List;

import clinica.version_java.modules.personas.models.Persona;
import clinica.version_java.modules.personas.models.enums.Estado;

public interface TelefonoPersonaService {

    public void guardarTelefonos(Persona persona, List<String> telefonos);

    public boolean agregarTelefono(int idPersona, String telefono) throws Exception;

    public boolean actualizarTelefono(String telefonoNuevo, String telefonoViejo, int idPersona, Estado estado) throws Exception;
}
