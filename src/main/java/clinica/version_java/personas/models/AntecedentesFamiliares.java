package clinica.version_java.personas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@NoArgsConstructor
@Entity
@Table(name = "antecedentes_familiares")
@ToString(exclude = { "paciente", "familiar" })
@Data
public class AntecedentesFamiliares {

    @Id
    @Column(name = "id_antecendetes_familiares")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAntecedentesFamiliares;
    @Column(name = "antecedentes")
    private String antecedentes;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.ACTIVO;

    @ManyToOne
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_persona")
    @JsonIgnore
    private Persona paciente;
    @ManyToOne
    @JoinColumn(name = "id_familiar", referencedColumnName = "id_persona")
    @JsonIgnore
    private Persona familiar;

    public AntecedentesFamiliares(String antecedentes, Persona paciente, Persona familiar) {
        this.antecedentes = antecedentes;
        this.paciente = paciente;
        this.familiar = familiar;
    }

}
