package clinica.version_java.DTOs;

import clinica.version_java.models.enums.Relacion;

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
public class DTOContactosEmergencia extends DTOPersonaBase {

    private Relacion relacion;
    private int idPersona;
    
    public DTOContactosEmergencia(DTOPersona dto, Relacion relacion) {
        super(dto);
        this.relacion = relacion;
    }
    public DTOContactosEmergencia(DTOPersonaBase dto, Relacion relacion) {
        super(dto);
        this.relacion = relacion;
    }
    
    
}
