package clinica.version_java.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.version_java.DTOs.DTOContactosEmergencia;
import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.Exceptions.contactoEmergenciaExceptions.ContactoEmergenciaNotFoundException;
import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.models.ContactoEmergencia;
import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.repositories.ContactoEmergenciaRepository;
import clinica.version_java.repositories.PersonaRepository;
import jakarta.transaction.Transactional;

@Service
public class ContactosEmergenciaService {

    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    TelefonoPersonaService telefonoPersonaService;
    @Autowired
    CorreoPersonaService correoPersonaService;
    @Autowired
    ContactoEmergenciaRepository contactoEmergenciaRepository;

    public void guardarInformacionPersonaRelacionada(List<DTOContactosEmergencia> contactosEmergencia, Persona persona)
            throws Exception {
        try {
            if (contactosEmergencia == null || contactosEmergencia.isEmpty())
                return;

            List<Persona> personasGuardadas = contactosEmergencia.stream()
                    .map(dto -> personaRepository.findByDui(dto.getDui())
                            .orElseGet(() -> new Persona(dto)))
                    .toList();
            personasGuardadas = personaRepository.saveAll(personasGuardadas);
            List<ContactoEmergencia> contactosGuardados = new ArrayList<>();

            for (int i = 0; i < contactosEmergencia.size(); i++) {
                telefonoPersonaService.guardarTelefonos(personasGuardadas.get(i),
                        contactosEmergencia.get(i).getTelefonos());
                correoPersonaService.guardarCorreos(personasGuardadas.get(i), contactosEmergencia.get(i).getCorreos());
                if (!contactoEmergenciaRepository.existsByPacienteAndContactoAndEstado(persona,
                        personasGuardadas.get(i), Estado.ACTIVO))
                    contactosGuardados.add(new ContactoEmergencia(contactosEmergencia.get(i).getRelacion(), persona,
                            personasGuardadas.get(i)));
            }

            contactoEmergenciaRepository.saveAll(contactosGuardados);
        } catch (Exception e) {
            throw new Exception("Error al guardar los contactos de emergencia", e);
        }

    }

    @Transactional
    public boolean eliminarContactoEmergencia(int idPaciente, int idContacto) {
        ContactoEmergencia contactoEmergencia = contactoEmergenciaRepository
                .findByContactoAndPaciente(idContacto, idPaciente)
                .orElseThrow(() -> new ContactoEmergenciaNotFoundException("No existe el registro "));
        contactoEmergencia.setEstado(Estado.INACTIVO);
        contactoEmergenciaRepository.save(contactoEmergencia);
        return true;
    }

    public List<DTOContactosEmergencia> obtenerContactos(int idPaciente) {
        List<ContactoEmergencia> contactos = contactoEmergenciaRepository.findContactosByPacienteId(idPaciente);
        return contactos.stream()
                .map(ce -> new DTOContactosEmergencia(new DTOPersonaBase(ce.getContacto()), ce.getRelacion()))
                .toList();
    }

    @Transactional
    public void agregarContacto(DTOContactosEmergencia contactosEmergencia)
            throws PersonaNotFoundException, Exception {
        Persona persona = personaRepository.findById(contactosEmergencia.getIdPersona())
                .orElseThrow(() -> new PersonaNotFoundException("la persona no existe"));

        try {
            guardarInformacionPersonaRelacionada(Collections.singletonList(contactosEmergencia), persona);

        } catch (Exception e) {
            throw new RuntimeException("error al agregar al antecedente");
        }

    }

}
