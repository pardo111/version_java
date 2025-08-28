package clinica.version_java.DTOs;

import java.util.List;


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

    
}
