package clinica.version_java.models;

import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Relacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "contacto_emergencia")
@Data
public class ContactoEmergencia {
    
    @Id
    @Column(name = "id_contacto_emergencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContactoPersona;
    @Enumerated(EnumType.STRING)
    private Relacion relacion;
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;

    @ManyToOne
    @JoinColumn(name="id_paciente", referencedColumnName = "id_persona")
    private Persona paciente;
    @ManyToOne
    @JoinColumn(name="id_contacto", referencedColumnName = "id_persona")
    private Persona contacto;

    
    public ContactoEmergencia(Relacion relacion, Persona paciente, Persona contacto) {
        this.relacion = relacion;
        this.paciente = paciente;
        this.contacto = contacto;
    }



    
}
