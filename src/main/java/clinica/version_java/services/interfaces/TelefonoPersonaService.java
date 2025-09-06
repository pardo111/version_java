package clinica.version_java.services.interfaces;

import java.util.List;

import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;

public interface TelefonoPersonaService {

    public void guardarTelefonos(Persona persona, List<String> telefonos);

    public boolean agregarTelefono(int idPersona, String telefono);

    public boolean actualizarTelefono(String telefonoNuevo, String telefonoViejo, int idPersona, Estado estado);
}
