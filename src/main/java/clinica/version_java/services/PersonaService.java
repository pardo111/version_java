package clinica.version_java.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import clinica.version_java.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.DTOs.DTOContactosEmergencia;
import clinica.version_java.DTOs.DTOPersona;
import clinica.version_java.DTOs.DTOPersonaBase;
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
            TelefonoPersonaRepository telefonoPersonaRepository, CorreoPersonaRepository correoPersonaRepository,
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
    public DTOPersona guardarPersonaCompleta(DTOPersona persona) throws Exception {

        try {
            Persona personaGuardada = personaRepository.findByDui(persona.getDui())
                    .orElseGet(() -> personaRepository.save(new Persona(persona)));

            guardarTelefonos(personaGuardada, persona.getTelefonos());
            guardarCorreos(personaGuardada, persona.getCorreos());
            guardarInformacionPersonaRelacionada(persona.getContactosEmergencia(), personaGuardada);
            guardarInformacionPersonaRelacionada(personaGuardada, persona.getAntecedentesFamiliares());

            persona.setIdPersona(personaGuardada.getIdPersona());
            return persona;
        } catch (Exception e) {
            throw new Exception("Error al crear la persona completa", e);
        }

    }

    /*
     * Metodo de lectura de Personas
     */
    public Page<DTOPersonaBase> obtenerPersonas(Pageable pageable, TipoPersona tipoPersona) {

        Page<Persona> page = personaRepository.findByEstadoAndTipoPersona(Estado.ACTIVO, tipoPersona , pageable );
        return page.map(DTOPersonaBase::new);
    }



    /*
     * metodos para aplicar a la barra de busqueda para buscar por campos de
     *          nombre
     *          dui
     *          sexo
     * 
     * 
     * @param nombre de la columna a buscar
     * @return Page<DTOPersonaBase>  de coincidencias
    */

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

    /*
     * obtener los contactos de emergencia de una persona
     * 
     * @param id de la persona a la que le buscare los contactos
     * 
     * @return DTOContactosEmergencia que tenga la persona si tiene
     * 
     */
    public List<DTOContactosEmergencia> obtenerContactos(int idPaciente) {
        List<ContactoEmergencia> contactos = contactoEmergenciaRepository.findContactosByPacienteId(idPaciente);

        return contactos.stream()
                .map(ce -> new DTOContactosEmergencia(new DTOPersonaBase(ce.getContacto()), ce.getRelacion()))
                .toList();
    }

    /*
     * obtener los antecedentes familiares de una persona
     * 
     * @param id de la persona a la que le buscare los antecedentes
     * 
     * @return DTOAntecedentesFamiliares que tenga la persona si tiene
     * 
     */

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
