package clinica.version_java.modules.personas.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import clinica.version_java.modules.personas.DTOs.DTOPersonaBase;
import clinica.version_java.modules.personas.models.enums.Estado;
import clinica.version_java.modules.personas.models.enums.Sexo;
import clinica.version_java.modules.personas.models.enums.TipoPersona;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*  
 * Esta clase representa una entidad de Persona en la base de datos.
 * Contiene atributos básicos como nombres, apellidos, fecha de nacimiento, dirección,
 * estado, sexo, DUI y tipo de persona. Además, define relaciones con otras entidades
 * como correos, teléfonos, contactos de emergencia, antecedentes familiares y citas. 
 * <p>Se incluyen constructores que permiten crear instancias
 * a partir de otros objetos Persona o DTOs para facilitar la
 * conversión entre capas.</p>  
 */

@NoArgsConstructor
@Entity
@ToString(exclude = {"correoPersona", "telefonoPersona"})
@Data
@Table(name = "persona")
public class Persona {

    // ATRIBUTOS
    // Se usa @Data de Lombok para generar getters, setters, toString, equals y
    // hashCode automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private int idPersona;
    @Column(name = "nombres")
    private String nombres;// Nombres de la persona
    @Column(name = "apellidos")
    private String apellidos;// Apellidos de la persona
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;// Fecha de nacimiento de la persona
    @Column(name = "direccion", length = 500)
    private String direccion;// Dirección de la persona
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.ACTIVO;// Estado de la persona, por defecto es ACTIVO
    // Puede ser ACTIVO, INACTIVO, ELIMINADO, etc.
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;// Sexo de la persona, puede ser MASCULINO, FEMENINO
    @Column(name = "dui", length = 10, nullable = false, unique = true)
    private String dui;// Documento Único de Identidad, debe ser único y no nulo
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoPersona tipoPersona; // Tipo de persona, puede ser PACIENTE, MEDICO, ADMINISTRATIVO, etc.
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    // RELACIONES
    // -----------------------------------------------------------------------------------
    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<CorreoPersona> correoPersona = new HashSet<>();
    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<TelefonoPersona> telefonoPersona = new HashSet<>();
    @OneToMany(mappedBy = "contacto")
    @JsonManagedReference
    private Set<ContactoEmergencia> contacto = new HashSet<>();
    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference
    private Set<ContactoEmergencia> pacienteContacto = new HashSet<>();
    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference
    private Set<AntecedentesFamiliares> pacienteFamiliar = new HashSet<>();
    @OneToMany(mappedBy = "familiar")
    @JsonManagedReference
    private Set<AntecedentesFamiliares> familiar = new HashSet<>();
   

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Persona))
            return false;
        Persona persona = (Persona) o;
        return Objects.equals(idPersona, persona.idPersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona);
    }

    // CONSTRUCTORES
    // -----------------------------------------------------------------------------------
    // Constructor por defecto
    public Persona(Persona persona) {
        this.nombres = persona.nombres;
        this.apellidos = persona.apellidos;
        this.fechaNacimiento = persona.fechaNacimiento;
        this.direccion = persona.direccion;
        this.sexo = persona.sexo;
        this.dui = persona.dui;
        this.tipoPersona = persona.tipoPersona;
        this.correoPersona = persona.correoPersona;
        this.telefonoPersona = persona.telefonoPersona;
        this.contacto = persona.contacto;
    }

    public Persona(DTOPersonaBase persona) {
        this.nombres = persona.getNombres();
        this.apellidos = persona.getApellidos();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.direccion = persona.getDireccion();
        this.sexo = persona.getSexo();
        this.dui = persona.getDui();
        this.tipoPersona = persona.getTipoPersona();
    }


    public Persona (String nombres, 
                    String apellidos, 
                    LocalDate fechaNacimiento, 
                    String direccion, 
                    Sexo sexo, 
                    String dui, 
                    TipoPersona tipoPersona
                    ) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.sexo = sexo;
        this.dui = dui;
        this.tipoPersona = tipoPersona;
    }
}
