package clinica.version_java.DTOs;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
 
import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.Persona;
import clinica.version_java.models.enums.Sexo;
import clinica.version_java.models.enums.TipoPersona;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class DTOPersonaBase {
    private int idPersona;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String direccion;
    private Estado estado;
    private Sexo sexo;
    private String dui;
    private TipoPersona tipoPersona;
    private List<String> correos = new ArrayList<>();
    private List<String> telefonos = new ArrayList<>();

    public DTOPersonaBase(DTOPersonaBase dto) {
        this.idPersona = dto.getIdPersona();
        this.nombres = dto.getNombres();
        this.estado = dto.getEstado();
        this.apellidos = dto.getApellidos();
        this.fechaNacimiento = dto.getFechaNacimiento();
        this.direccion = dto.getDireccion();
        this.sexo = dto.getSexo();
        this.dui = dto.getDui();
        this.tipoPersona = dto.getTipoPersona();
        this.correos = dto.getCorreos();
        this.telefonos = dto.getTelefonos();
    }

    public DTOPersonaBase(DTOPersona dto) {
        this.nombres = dto.getNombres();
        this.estado = dto.getEstado();
        this.idPersona = dto.getIdPersona();
        this.apellidos = dto.getApellidos();
        this.fechaNacimiento = dto.getFechaNacimiento();
        this.direccion = dto.getDireccion();
        this.sexo = dto.getSexo();
        this.dui = dto.getDui();
        this.tipoPersona = dto.getTipoPersona();
        this.correos = dto.getCorreos();
        this.telefonos = dto.getTelefonos();
    }

    public DTOPersonaBase(DTOAntecedentesFamiliares dto) {
        this.nombres = dto.getNombres();
        this.apellidos = dto.getApellidos();
        this.idPersona = dto.getIdPersona();
        this.fechaNacimiento = dto.getFechaNacimiento();
        this.direccion = dto.getDireccion();
        this.sexo = dto.getSexo();
        this.dui = dto.getDui();
        this.tipoPersona = dto.getTipoPersona();
        this.correos = dto.getCorreos();
        this.telefonos = dto.getTelefonos();
        this.estado = dto.getEstado();

    }

    public DTOPersonaBase(DTOContactosEmergencia dto) {
        this.nombres = dto.getNombres();
        this.apellidos = dto.getApellidos();
        this.fechaNacimiento = dto.getFechaNacimiento();
        this.direccion = dto.getDireccion();
        this.sexo = dto.getSexo();
        this.idPersona = dto.getIdPersona();
        this.dui = dto.getDui();
        this.tipoPersona = dto.getTipoPersona();
        this.correos = dto.getCorreos();
        this.telefonos = dto.getTelefonos();
        this.estado = dto.getEstado();

    }

    public DTOPersonaBase(
            int idPersona,
            String nombres,
            String apellidos,
            LocalDate fechaNacimiento,
                String direccion,
            Sexo sexo,
            String dui,
            TipoPersona tipoPersona) {
        this.idPersona = idPersona;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.sexo = sexo;
        this.dui = dui;
        this.tipoPersona = tipoPersona;
    }
public DTOPersonaBase(Persona p) {
    this.idPersona = p.getIdPersona();
    this.nombres = p.getNombres();
    this.apellidos = p.getApellidos();
    this.fechaNacimiento = p.getFechaNacimiento();
    this.direccion = p.getDireccion();
    this.sexo = p.getSexo();
    this.dui = p.getDui();
    this.tipoPersona = p.getTipoPersona();

    if (p.getCorreoPersona() != null) {
        p.getCorreoPersona().forEach(c -> this.correos.add(c.getCorreo()));
    }
    if (p.getTelefonoPersona() != null) {
        p.getTelefonoPersona().forEach(t -> this.telefonos.add(t.getTelefono()));
    }
}

}
