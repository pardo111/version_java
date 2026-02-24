package clinica.version_java.modules.citas.models;

import java.util.ArrayList;
import java.util.List;

import clinica.version_java.modules.personas.models.enums.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "diagnosticos_principales")
public class DiagnosticoPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diagnostico_principal")
    private int id;
    @Column(name = "diagnostico", length = 100)
    private String diagnostico;
    @Column(name = "criterios", length = 500)
    private String criterios;
    @Column(name = "justificacion", length = 500)
    private String justificacion;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;
    @ManyToMany
    @JoinTable(name = "diagnostico_principal_hipotetico", // tabla intermedia
            joinColumns = @JoinColumn(name = "id_diagnostico_principal"), inverseJoinColumns = @JoinColumn(name = "id_diagnostico_hipotetico"))
    private List<DiagnosticoHipotetico> diagnosticoHipoteticos = new ArrayList<>();

    @OneToOne(mappedBy = "diagnosticoPrincipal")
    private InformeDiagnostico informeDiagnostico;

}
