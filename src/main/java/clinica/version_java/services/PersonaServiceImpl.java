package clinica.version_java.services;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import clinica.version_java.DTOs.DTOPersona;
import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.Exceptions.personaExceptions.PersonaSaveException;
import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Sexo;
import clinica.version_java.models.enums.TipoPersona;
import clinica.version_java.repositories.AntecedentesFamiliaresRepository;
import clinica.version_java.repositories.ContactoEmergenciaRepository;
import clinica.version_java.repositories.CorreoPersonaRepository;
import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.repositories.TelefonoPersonaRepository;
import clinica.version_java.services.interfaces.AntecedentesFamiliaresService;
import clinica.version_java.services.interfaces.ContactosEmergenciaService;
import clinica.version_java.services.interfaces.CorreoPersonaService;
import clinica.version_java.services.interfaces.PersonaService;
import clinica.version_java.services.interfaces.TelefonoPersonaService;
import jakarta.transaction.Transactional;

/**
 * Servicio para la gestión de personas, incluyendo creación y actualización
 * de datos básicos, teléfonos, correos, contactos de emergencia y antecedentes
 * familiares.
 * <p>
 * Contiene métodos transaccionales y privados para mantener la integridad de
 * los datos
 * y asegurar la consistencia en la base de datos.
 * </p>
 */
@Service
public class PersonaServiceImpl implements PersonaService {
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    ContactoEmergenciaRepository contactoEmergenciaRepository;
    @Autowired
    TelefonoPersonaRepository telefonoPersonaRepository;
    @Autowired
    CorreoPersonaRepository correoPersonaRepository;
    @Autowired
    AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;
    @Autowired
    CorreoPersonaService correoPersonaService;
    @Autowired
    TelefonoPersonaService telefonoPersonaService;
    @Autowired
    ContactosEmergenciaService contactosEmergenciaService;
    @Autowired
    AntecedentesFamiliaresService antecedentesFamiliaresService;

    /**
     * Crea o actualiza una persona completa con sus datos relacionados.
     * <p>
     * Este método es transaccional, por lo que si ocurre un error en cualquiera
     * de las operaciones, se revertirán todas las modificaciones en la base de
     * datos.
     * </p>
     *
     * @param persona DTO con toda la información de la persona a guardar
     * @return DTO actualizado con el id generado de la persona
     * @throws Exception si ocurre un error al guardar la información
     */
    @Transactional(rollbackOn = Exception.class)
    public DTOPersona guardarOActualizarPersonaCompleta(DTOPersona persona) throws PersonaSaveException {

        try {
            Persona personaGuardada;

            if (personaRepository.existsByDui(persona.getDui())) {
                // para actualizar persona
                personaGuardada = personaRepository.findByDui(persona.getDui())
                        .orElseThrow(() -> new PersonaNotFoundException("persona no existe"));

                personaGuardada
                        .setNombres(persona.getNombres() != null ? persona.getNombres() : personaGuardada.getNombres());
                personaGuardada.setApellidos(
                        persona.getApellidos() != null ? persona.getApellidos() : personaGuardada.getApellidos());
                personaGuardada
                        .setEstado(persona.getEstado() != null ? persona.getEstado() : personaGuardada.getEstado());
                personaGuardada.setFechaNacimiento(persona.getFechaNacimiento() != null ? persona.getFechaNacimiento()
                        : personaGuardada.getFechaNacimiento());
                personaGuardada.setDireccion(
                        persona.getDireccion() != null ? persona.getDireccion() : personaGuardada.getDireccion());

            } else {
                // para crear una nueva persona
                personaGuardada = personaRepository.save(new Persona(persona));
            }

            telefonoPersonaService.guardarTelefonos(personaGuardada, persona.getTelefonos());
            correoPersonaService.guardarCorreos(personaGuardada, persona.getCorreos());
            contactosEmergenciaService.guardarInformacionPersonaRelacionada(persona.getContactosEmergencia(),
                    personaGuardada);
            antecedentesFamiliaresService.guardarInformacionPersonaRelacionada(personaGuardada,
                    persona.getAntecedentesFamiliares());

            persona.setIdPersona(personaGuardada.getIdPersona());
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersonaSaveException("Error al crear la persona completa" + e.getMessage());
        }

    }

    public Page<DTOPersonaBase> obtenerPersonas(Pageable pageable, TipoPersona tipoPersona) {
        Page<Persona> page = personaRepository.findByEstadoAndTipoPersona(Estado.ACTIVO, tipoPersona, pageable);
        return page.map(DTOPersonaBase::new);
    }

    public Page<DTOPersonaBase> obtenerBusquedaSimilarNombre(Pageable pageable, String nombre) {
        return personaRepository.findByEstadoAndNombresContainingIgnoreCase(Estado.ACTIVO, nombre, pageable)
                .map(DTOPersonaBase::new);
    }

    public Page<DTOPersonaBase> obtenerPorDui(Pageable pageable, String dui) {
        return personaRepository.findByDui(dui, pageable).map(DTOPersonaBase::new);
    }

    public Page<DTOPersonaBase> obtenerPorSexo(Pageable pageable, Sexo sexo) {
        return personaRepository.findByEstadoAndSexo(Estado.ACTIVO, sexo, pageable).map(DTOPersonaBase::new);
    }

}
