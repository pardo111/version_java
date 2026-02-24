package clinica.version_java.modules.personas.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class DTOAntecedentesFamiliares extends DTOPersonaBase {

    private String antecedente;
    private int idPersona;
    public DTOAntecedentesFamiliares(DTOPersona dto, String antecedente) {
        super(dto);
        this.antecedente = antecedente;
    }

    public DTOAntecedentesFamiliares(DTOPersonaBase dto, String antecedente) {
        super(dto);
        this.antecedente = antecedente;
    }

}
