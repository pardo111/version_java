package clinica.version_java.services;

import org.springframework.stereotype.Service;

import clinica.version_java.repositories.AntecedentesFamiliaresRepository;
import clinica.version_java.repositories.ContactoEmergenciaRepository;
import clinica.version_java.repositories.CorreoPersonaRepository;
import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.repositories.TelefonoPersonaRepository;

@Service
public class PersonaService {
    PersonaRepository personaRepository;
    ContactoEmergenciaRepository contactoEmergenciaRepository;
    TelefonoPersonaRepository telefonoPersonaRepository;
    CorreoPersonaRepository correoPersonaRepository;
    AntecedentesFamiliaresRepository    antecedentesFamiliaresRepository;

    public PersonaService(PersonaRepository personaRepository,
            ContactoEmergenciaRepository contactoEmergenciaRepository,
            TelefonoPersonaRepository telefonoPersonaRepository, CorreoPersonaRepository correoPersonaRepository,
            AntecedentesFamiliaresRepository antecedentesFamiliaresRepository) {
        this.personaRepository = personaRepository;
        this.contactoEmergenciaRepository = contactoEmergenciaRepository;
        this.telefonoPersonaRepository = telefonoPersonaRepository;
        this.correoPersonaRepository = correoPersonaRepository;
        this.antecedentesFamiliaresRepository = antecedentesFamiliaresRepository;
    }


    
    

    
}
