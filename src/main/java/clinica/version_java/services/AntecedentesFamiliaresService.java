package clinica.version_java.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.version_java.DTOs.DTOAntecedentesFamiliares;
import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.Exceptions.personaExceptions.PersonaNotFoundException;
import clinica.version_java.models.AntecedentesFamiliares;
import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Estado;
import clinica.version_java.repositories.AntecedentesFamiliaresRepository;
import clinica.version_java.repositories.PersonaRepository;
import jakarta.transaction.Transactional;

@Service
public class AntecedentesFamiliaresService {

    @Autowired
    AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    TelefonoPersonaService telefonoPersonaService;
    @Autowired
    CorreoPersonaService correoPersonaService;

    public List<DTOAntecedentesFamiliares> obtenerAntecedentes(int idPaciente) {
        List<AntecedentesFamiliares> contactos = antecedentesFamiliaresRepository.findContactosByPacienteId(idPaciente);
        return contactos.stream()
                .map(ce -> new DTOAntecedentesFamiliares(new DTOPersonaBase(ce.getFamiliar()), ce.getAntecedentes()))
                .toList();
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


    

    public void guardarInformacionPersonaRelacionada(Persona persona,
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

                telefonoPersonaService.guardarTelefonos(personasGuardadas.get(i),
                        antecedentesFamiliares.get(i).getTelefonos());
                correoPersonaService.guardarCorreos(personasGuardadas.get(i), antecedentesFamiliares.get(i).getCorreos());
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
