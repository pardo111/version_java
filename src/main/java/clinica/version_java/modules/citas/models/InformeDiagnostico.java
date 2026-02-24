package clinica.version_java.modules.citas.models;

import clinica.version_java.modules.personas.models.enums.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "informes_diagnosticos")
public class InformeDiagnostico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informe_diagnostico")
    private int id;

    @Column(name = "resumen_diagnostico", length = 1000)
    private String resumenDiagnostico;
    @Column(name = "implicaciones", length = 1000)
    private String implicaciones;
    @Column(name = "recomendaciones", length = 1000)
    private String recomendaciones;
    @Column(name="estado")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;



    @OneToOne
    @JoinColumn(name = "id_diagnostico_principal", referencedColumnName = "id_diagnostico_principal"    )
    private DiagnosticoPrincipal diagnosticoPrincipal;
}
