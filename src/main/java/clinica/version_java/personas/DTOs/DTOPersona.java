package clinica.version_java.personas.DTOs;

import java.time.LocalDate;
import java.util.List;

import clinica.version_java.personas.models.enums.Sexo;
import clinica.version_java.personas.models.enums.TipoPersona;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class DTOPersona extends DTOPersonaBase {

    List<DTOContactosEmergencia> contactosEmergencia;
    List<DTOAntecedentesFamiliares> antecedentesFamiliares;

    public DTOPersona(DTOPersona dto) {
        super(dto);
    }

    public DTOPersona(DTOPersonaBase dto) {
        super(dto);
    }

    public DTOPersona(
            int idPersona,
            String nombres,
            String apellidos,
            LocalDate fechaNacimiento,
            String direccion,
            Sexo sexo,
            String dui,
            TipoPersona tipoPersona) {
        super(
                idPersona,
                nombres,
                apellidos,
                fechaNacimiento,
                direccion,
                sexo,
                dui,
                tipoPersona);
    }

}
