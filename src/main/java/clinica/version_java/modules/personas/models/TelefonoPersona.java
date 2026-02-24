package clinica.version_java.modules.personas.models;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import clinica.version_java.modules.personas.models.enums.Estado;
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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "telefono_persona")
@ToString(exclude = {"persona"})
@Data
@NoArgsConstructor
public class TelefonoPersona {

    @Id
    @Column(name = "id_telefono_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTelefonoPersona;
    @Column(name = "telefono", length = 15, unique = true, nullable = false)
    private String telefono;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id_persona")
    @JsonBackReference
    private Persona persona;

    public TelefonoPersona(String telefono, Persona persona) {
        this.telefono = telefono;
        this.persona = persona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TelefonoPersona))
            return false;
        TelefonoPersona that = (TelefonoPersona) o;
        return Objects.equals(idTelefonoPersona, that.idTelefonoPersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTelefonoPersona); // SOLO id
    }

}
