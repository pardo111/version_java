package clinica.version_java.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import clinica.version_java.models.enums.Estado;
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
@Table(name="telefono_persona")
@Data
public class TelefonoPersona {

    @Id
    @Column(name="id_telefono_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTelefonoPersona;
    @Column(name="telefono"  , length = 15, unique = true, nullable = false )
    private String telefono;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id_persona")
    private Persona persona;

    public TelefonoPersona(String telefono, Persona persona) {
        this.telefono = telefono;
        this.persona = persona;
    }

    
}
