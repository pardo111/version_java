package clinica.version_java.modules.personas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.version_java.modules.personas.models.Persona;
import clinica.version_java.modules.personas.models.TelefonoPersona;
import clinica.version_java.modules.personas.models.enums.Estado;
import clinica.version_java.modules.personas.repositories.PersonaRepository;
import clinica.version_java.modules.personas.repositories.TelefonoPersonaRepository;
import clinica.version_java.modules.personas.services.interfaces.TelefonoPersonaService;
import jakarta.transaction.Transactional;

@Service
public class TelefonoPersonaServiceImpl implements TelefonoPersonaService {
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
    public boolean agregarTelefono(int idPersona, String telefono) throws Exception {
        return personaRepository.findById(idPersona)
                .map(persona -> {
                    TelefonoPersona telefonoPersona = new TelefonoPersona();
                    telefonoPersona.setTelefono(telefono);
                    telefonoPersona.setPersona(persona);
                    telefonoPersonaRepository.save(telefonoPersona);
                    return true;
                })
                .orElseThrow(() -> new Exception("no existe la persona pasada"));
    }

    @Transactional
    public boolean actualizarTelefono(String telefonoNuevo, String telefonoViejo, int idPersona, Estado estado) throws Exception {

        return personaRepository.findById(idPersona)
                .map(
                        persona -> {
                            TelefonoPersona telefonoActualizado = null;
                            try {
                                telefonoActualizado = telefonoPersonaRepository
                                        .findByPersonaAndTelefono(persona, telefonoViejo)
                                        .orElseThrow(() -> new Exception(
                                                "no existe el telefono buscado por persona y telefono"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            telefonoActualizado.setTelefono(telefonoNuevo);
                            telefonoActualizado.setEstado(estado);
                            telefonoPersonaRepository.save(telefonoActualizado);
                            return true;
                        })
                .orElseThrow(() -> new Exception("no existe la persona pasada"));

    }

}
