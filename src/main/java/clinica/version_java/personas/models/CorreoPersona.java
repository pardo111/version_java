package clinica.version_java.personas.models;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import clinica.version_java.personas.models.enums.Estado;
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
@Data
@Table(name = "correo_persona")
@ToString(exclude = {"persona"})
@NoArgsConstructor
public class CorreoPersona {

    @Id
    @Column(name = "id_correo_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCorreoPersona;
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;
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

    public CorreoPersona(String correo, Persona persona) {
        this.correo = correo;
        this.persona = persona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CorreoPersona))
            return false;
        CorreoPersona that = (CorreoPersona) o;
        return Objects.equals(idCorreoPersona, that.idCorreoPersona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCorreoPersona);
    }

}
