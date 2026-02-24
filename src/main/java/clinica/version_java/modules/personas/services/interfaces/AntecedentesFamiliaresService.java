package clinica.version_java.modules.personas.services.interfaces;

import java.util.List;

import clinica.version_java.modules.personas.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.modules.personas.models.Persona;

public interface AntecedentesFamiliaresService {
    public List<DTOAntecedentesFamiliares> obtenerAntecedentes(int idPaciente);

    public boolean eliminarAntecedenteFamiliar(int idPaciente, int idFamiliar);

    public void agregarAntecedente(DTOAntecedentesFamiliares antecedentesFamiliares)
            throws  Exception;

    public void guardarInformacionPersonaRelacionada(Persona persona,
            List<DTOAntecedentesFamiliares> antecedentesFamiliares) throws Exception;
}
