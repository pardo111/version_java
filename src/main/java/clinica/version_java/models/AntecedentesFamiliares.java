package clinica.version_java.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "antecedentes_familiares")
@Data
public class AntecedentesFamiliares {
    

    @Id
    @Column(name = "id_antecendetes_familiares")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAntecedentesFamiliares;
    @Column(name = "antecedentes")
    private String antecedentes;


    @ManyToOne
    @JoinColumn(name="id_paciente", referencedColumnName = "id_persona")
    private Persona paciente;
    @ManyToOne
    @JoinColumn(name = "id_familiar", referencedColumnName = "id_persona")
    private Persona familiar;


    public AntecedentesFamiliares(String antecedentes, Persona paciente, Persona familiar) {
        this.antecedentes = antecedentes;
        this.paciente = paciente;
        this.familiar = familiar;
    }

    
}
