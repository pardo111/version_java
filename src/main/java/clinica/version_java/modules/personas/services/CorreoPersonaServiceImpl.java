package clinica.version_java.modules.personas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.version_java.modules.personas.models.CorreoPersona;
import clinica.version_java.modules.personas.models.Persona;
import clinica.version_java.modules.personas.models.enums.Estado;
import clinica.version_java.modules.personas.repositories.CorreoPersonaRepository;
import clinica.version_java.modules.personas.repositories.PersonaRepository;
import clinica.version_java.modules.personas.services.interfaces.CorreoPersonaService;
import jakarta.transaction.Transactional;

@Service
public class CorreoPersonaServiceImpl implements CorreoPersonaService {

    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    CorreoPersonaRepository correoPersonaRepository;

    @Transactional
    public boolean actualizarCorreo(String correoNuevo, String correoAntiguo, int idPersona, Estado estado) throws Exception {

        return personaRepository.findById(idPersona)
                .map(persona -> {
                    CorreoPersona correoActualizado = null;
                    try {
                        correoActualizado = correoPersonaRepository
                                .findByPersonaAndCorreo(persona, correoAntiguo)
                                .orElseThrow(
                                        () -> new Exception(
                                                "no se hallo el registro buscado por persona y correo"));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    correoActualizado.setCorreo(correoNuevo);
                    correoActualizado.setEstado(estado);
                    correoPersonaRepository.save(correoActualizado);
                    return true;
                })
                .orElseThrow(() -> new Exception("no existe la persona pasada"));

    }

    @Transactional
    public boolean agregarCorreo(int idPersona, String correo)  throws Exception {
        return personaRepository.findById(idPersona)
                .map(persona -> {
                    CorreoPersona correoNuevo = new CorreoPersona();
                    correoNuevo.setCorreo(correo);
                    correoNuevo.setPersona(persona);
                    correoPersonaRepository.save(correoNuevo);
                    return true;
                })
                .orElseThrow(() -> new Exception("no existe la persona pasada"));
    }

    
    public void guardarCorreos(Persona persona, List<String> correos) {
        if (correos == null || correos.isEmpty())
            return;
        List<String> existentes = persona.getCorreoPersona().stream()
                .map(CorreoPersona::getCorreo)
                .toList();

        correoPersonaRepository.saveAll(
                correos.stream()
                        .filter(c -> !existentes.contains(c))
                        .map(t -> new CorreoPersona(t, persona))
                        .toList());
    }

}
