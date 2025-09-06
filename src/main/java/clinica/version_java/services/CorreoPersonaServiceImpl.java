package clinica.version_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.version_java.Exceptions.correoPersonaExceptions.CorreoPersonaNotFoundException;
import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.models.CorreoPersona;
import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.repositories.CorreoPersonaRepository;
import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.services.interfaces.CorreoPersonaService;
import jakarta.transaction.Transactional;

@Service
public class CorreoPersonaServiceImpl implements CorreoPersonaService {

    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    CorreoPersonaRepository correoPersonaRepository;

    @Transactional
    public boolean actualizarCorreo(String correoNuevo, String correoAntiguo, int idPersona, Estado estado) {

        return personaRepository.findById(idPersona)
                .map(persona -> {
                    CorreoPersona correoActualizado = correoPersonaRepository
                            .findByPersonaAndCorreo(persona, correoAntiguo).orElseThrow(
                                    () -> new CorreoPersonaNotFoundException(
                                            "no se hallo el registro buscado por persona y correo"));
                    correoActualizado.setCorreo(correoNuevo);
                    correoActualizado.setEstado(estado);
                    correoPersonaRepository.save(correoActualizado);
                    return true;
                })
                .orElseThrow(() -> new PersonaNotFoundException("no existe la persona pasada"));

    }

    @Transactional
    public boolean agregarCorreo(int idPersona, String correo) {
        return personaRepository.findById(idPersona)
                .map(persona -> {
                    CorreoPersona correoNuevo = new CorreoPersona();
                    correoNuevo.setCorreo(correo);
                    correoNuevo.setPersona(persona);
                    correoPersonaRepository.save(correoNuevo);
                    return true;
                })
                .orElseThrow(() -> new PersonaNotFoundException("no existe la persona pasada"));
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
