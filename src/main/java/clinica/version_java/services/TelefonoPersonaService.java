package clinica.version_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.Exceptions.telefonoPersonaException.TelefonoPersonaNotFoundException;
import clinica.version_java.models.Persona;
import clinica.version_java.models.TelefonoPersona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.repositories.TelefonoPersonaRepository;
import jakarta.transaction.Transactional;

@Service
public class TelefonoPersonaService {
    @Autowired
    TelefonoPersonaRepository telefonoPersonaRepository;
    @Autowired
    PersonaRepository personaRepository;

    public void guardarTelefonos(Persona persona, List<String> telefonos) {
        if (telefonos == null || telefonos.isEmpty())
            return;

        List<String> existentes = persona.getTelefonoPersona().stream()
                .map(TelefonoPersona::getTelefono)
                .toList();

        telefonoPersonaRepository.saveAll(
                telefonos.stream()
                        .filter(t -> !existentes.contains(t))
                        .map(t -> new TelefonoPersona(t, persona))
                        .toList());
    }

    @Transactional
    public boolean agregarTelefono(int idPersona, String telefono) {
        return personaRepository.findById(idPersona)
                .map(persona -> {
                    TelefonoPersona telefonoPersona = new TelefonoPersona();
                    telefonoPersona.setTelefono(telefono);
                    telefonoPersona.setPersona(persona);
                    telefonoPersonaRepository.save(telefonoPersona);
                    return true;
                })
                .orElseThrow(() -> new PersonaNotFoundException("no existe la persona pasada"));
    }

    @Transactional
    public boolean actualizarTelefono(String telefonoNuevo, String telefonoViejo, int idPersona, Estado estado) {

        return personaRepository.findById(idPersona)
                .map(
                        persona -> {
                            TelefonoPersona telefonoActualizado = telefonoPersonaRepository
                                    .findByPersonaAndTelefono(persona, telefonoViejo)
                                    .orElseThrow(() -> new TelefonoPersonaNotFoundException(
                                            "no existe el telefono buscado por persona y telefono"));
                            telefonoActualizado.setTelefono(telefonoNuevo);
                            telefonoActualizado.setEstado(estado);
                            telefonoPersonaRepository.save(telefonoActualizado);
                            return true;
                        })
                .orElseThrow(() -> new PersonaNotFoundException("no existe la persona pasada"));

    }

}
