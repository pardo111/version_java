package clinica.version_java.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import clinica.version_java.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.DTOs.DTOContactosEmergencia;
import clinica.version_java.DTOs.DTOPersona;
import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.Exceptions.contactoEmergenciaExceptions.ContactoEmergenciaNotFoundException;
import clinica.version_java.Exceptions.correoPersonaExceptions.CorreoPersonaNotFoundException;
import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.Exceptions.personaExceptions.PersonaSaveException;
import clinica.version_java.Exceptions.telefonoPersonaException.TelefonoPersonaNotFoundException;
import clinica.version_java.models.AntecedentesFamiliares;
import clinica.version_java.models.ContactoEmergencia;
import clinica.version_java.models.CorreoPersona;
import clinica.version_java.models.Persona;
import clinica.version_java.models.TelefonoPersona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Sexo;
import clinica.version_java.models.enums.TipoPersona;
import clinica.version_java.repositories.AntecedentesFamiliaresRepository;
import clinica.version_java.repositories.ContactoEmergenciaRepository;
import clinica.version_java.repositories.CorreoPersonaRepository;
import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.repositories.TelefonoPersonaRepository;
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
public class PersonaService {
    PersonaRepository personaRepository;
    ContactoEmergenciaRepository contactoEmergenciaRepository;
    TelefonoPersonaRepository telefonoPersonaRepository;
    CorreoPersonaRepository correoPersonaRepository;
    AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;

    /**
     * Constructor para inyección de dependencias de repositorios.
     */
    public PersonaService(PersonaRepository personaRepository,
            ContactoEmergenciaRepository contactoEmergenciaRepository,
            TelefonoPersonaRepository telefonoPersonaRepository,
            CorreoPersonaRepository correoPersonaRepository,
            AntecedentesFamiliaresRepository antecedentesFamiliaresRepository) {
        this.personaRepository = personaRepository;
        this.contactoEmergenciaRepository = contactoEmergenciaRepository;
        this.telefonoPersonaRepository = telefonoPersonaRepository;
        this.correoPersonaRepository = correoPersonaRepository;
        this.antecedentesFamiliaresRepository = antecedentesFamiliaresRepository;
    }

    // -----------------------------------TRANSACCIONES-----------------------------------

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
                personaGuardada.setNombres(persona.getNombres());
                personaGuardada.setApellidos(persona.getApellidos());
                personaGuardada.setEstado(persona.getEstado());
                personaGuardada.setFechaNacimiento(persona.getFechaNacimiento());
                personaGuardada.setDireccion(persona.getDireccion());

            } else {
                // para crear una nueva persona
                personaGuardada = personaRepository.save(new Persona(persona));
            }

            guardarTelefonos(personaGuardada, persona.getTelefonos());
            guardarCorreos(personaGuardada, persona.getCorreos());
            guardarInformacionPersonaRelacionada(persona.getContactosEmergencia(), personaGuardada);
            guardarInformacionPersonaRelacionada(personaGuardada, persona.getAntecedentesFamiliares());

            persona.setIdPersona(personaGuardada.getIdPersona());
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersonaSaveException("Error al crear la persona completa" + e.getMessage());
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
    public boolean eliminarAntecedenteFamiliar(int idPaciente, int idFamiliar) throws PersonaNotFoundException {

        try {

            Persona paciente = personaRepository.findById(idPaciente).orElseThrow(
                    () -> new PersonaNotFoundException("no se encontro a la persona por el id " + idPaciente));
            Persona familiar = personaRepository.findByIdPersona(idFamiliar);
            AntecedentesFamiliares antecedentes = antecedentesFamiliaresRepository
                    .findByPacienteAndFamiliar(paciente, familiar).orElseGet(null);
            antecedentes.setEstado(Estado.INACTIVO);
            antecedentesFamiliaresRepository.save(antecedentes);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Transactional
    public void agregarAntecedente(DTOAntecedentesFamiliares antecedentesFamiliares)
            throws PersonaNotFoundException, Exception {
        Persona persona = personaRepository.findById(antecedentesFamiliares.getIdPersona())
                .orElseThrow(() -> new PersonaNotFoundException("la persona no existe"));

        try {
            guardarInformacionPersonaRelacionada(persona, Collections.singletonList(antecedentesFamiliares));

        } catch (Exception e) {
            throw new RuntimeException("error al agregar al antecedente");
        }

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
    // -------------------------------------METODOS_DE_LECTURA---------------------------------

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

    public List<DTOContactosEmergencia> obtenerContactos(int idPaciente) {
        List<ContactoEmergencia> contactos = contactoEmergenciaRepository.findContactosByPacienteId(idPaciente);
        return contactos.stream()
                .map(ce -> new DTOContactosEmergencia(new DTOPersonaBase(ce.getContacto()), ce.getRelacion()))
                .toList();
    }

    public List<DTOAntecedentesFamiliares> obtenerAntecedentes(int idPaciente) {
        List<AntecedentesFamiliares> contactos = antecedentesFamiliaresRepository.findContactosByPacienteId(idPaciente);
        return contactos.stream()
                .map(ce -> new DTOAntecedentesFamiliares(new DTOPersonaBase(ce.getFamiliar()), ce.getAntecedentes()))
                .toList();
    }
    // -----------------------------------METODOS_PRIVADOS-----------------------------------
    // -------------------------------HELPER_METHODS-----------------------------------

    private void guardarTelefonos(Persona persona, List<String> telefonos) {
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

    private void guardarCorreos(Persona persona, List<String> correos) {
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

    private void guardarInformacionPersonaRelacionada(List<DTOContactosEmergencia> contactosEmergencia, Persona persona)
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
                guardarTelefonos(personasGuardadas.get(i), contactosEmergencia.get(i).getTelefonos());
                guardarCorreos(personasGuardadas.get(i), contactosEmergencia.get(i).getCorreos());
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

    private void guardarInformacionPersonaRelacionada(Persona persona,
            List<DTOAntecedentesFamiliares> antecedentesFamiliares)
            throws Exception {
        try {
            if (antecedentesFamiliares == null || antecedentesFamiliares.isEmpty())
                return;
            List<Persona> personasGuardadas = antecedentesFamiliares.stream()
                    .map(dto -> personaRepository.findByDui(dto.getDui())
                            .orElseGet(() -> new Persona(dto)))
                    .toList();

            personasGuardadas = personaRepository.saveAll(personasGuardadas);
            List<AntecedentesFamiliares> antecedentesGuardados = new ArrayList<>();

            for (int i = 0; i < antecedentesFamiliares.size(); i++) {

                guardarTelefonos(personasGuardadas.get(i), antecedentesFamiliares.get(i).getTelefonos());
                guardarCorreos(personasGuardadas.get(i), antecedentesFamiliares.get(i).getCorreos());
                if (!antecedentesFamiliaresRepository.existsByPacienteAndFamiliarAndEstado(persona,
                        personasGuardadas.get(i), Estado.ACTIVO))
                    antecedentesGuardados
                            .add(new AntecedentesFamiliares(antecedentesFamiliares.get(i).getAntecedente(), persona,
                                    personasGuardadas.get(i)));
            }

            antecedentesFamiliaresRepository.saveAll(antecedentesGuardados);
        } catch (Exception e) {
            throw new Exception("Error al guardar los antecedentes familiares", e);
        }

    }

}
