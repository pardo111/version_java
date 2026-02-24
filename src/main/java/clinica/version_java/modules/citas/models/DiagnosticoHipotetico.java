package clinica.version_java.modules.citas.models;

import java.util.ArrayList;
import java.util.List;

import clinica.version_java.modules.citas.models.enums.NivelImpacto;
import clinica.version_java.modules.personas.models.enums.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "diagnosticos_hipoteticos")
public class DiagnosticoHipotetico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diagnostico_hipotetico")
    private int id;
    @Column(name = "analisis_factores")
    private String analisisFactores;
    @Column(name = "impacto_bienestar")
    @Enumerated(EnumType.STRING)
    private NivelImpacto bienestar;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.ACTIVO;

    @ManyToOne
    @JoinColumn(name = "id_cita", referencedColumnName = "id_cita")
    private Citas cita;
    @ManyToMany(mappedBy = "diagnosticoHipoteticos")
    private List<DiagnosticoPrincipal> diagnosticosPrincipales = new ArrayList<>();

}
