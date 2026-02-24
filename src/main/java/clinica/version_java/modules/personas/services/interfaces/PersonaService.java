package clinica.version_java.modules.personas.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import clinica.version_java.modules.personas.DTOs.DTOPersona;
import clinica.version_java.modules.personas.DTOs.DTOPersonaBase;
import clinica.version_java.modules.personas.models.enums.Sexo;
import clinica.version_java.modules.personas.models.enums.TipoPersona;

public interface PersonaService {
    

    public Page<DTOPersonaBase> obtenerPorSexo(Pageable pageable, Sexo sexo);
    public Page<DTOPersonaBase> obtenerPorDui(Pageable pageable, String dui) ;
    public Page<DTOPersonaBase> obtenerBusquedaSimilarNombre(Pageable pageable, String nombre);
    public Page<DTOPersonaBase> obtenerPersonas(Pageable pageable, TipoPersona tipoPersona);
     public DTOPersona guardarOActualizarPersonaCompleta(DTOPersona persona) throws Exception ;
}
