package clinica.version_java.modules.citas.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import clinica.version_java.modules.personas.models.Persona;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
public class Citas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cita")
    private int idCita;
    @Column(name="codigo_cita", length = 6)
    private String codigo;
    @Column(name = "fecha_cita_inicio")
    private LocalDateTime fechaCitaInicio;
    @Column(name="fecha_cita_cierre")
    private LocalDateTime fechaCitaCierre;
    @Column(precision = 10, scale = 2)
    private BigDecimal precio;  
    @Column(name = "pagado")
    private boolean pagado;
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;
    @Column(name = "factura")
    private String factura;
    @Column(name = "asistio" )
    private boolean asistio;

    @ManyToOne
    @JoinColumn(name="id_paciente", referencedColumnName = "id_persona")
    private Persona paciente;
    @ManyToOne
    @JoinColumn(name="id_medico", referencedColumnName = "id_persona")
    private Persona medico;

    @OneToMany(mappedBy = "cita")
    private List<EstadoMental> estadosMentales = new ArrayList<>();
    @OneToMany(mappedBy = "cita")
    private List<InstrumentosEvaluacion> instrumentosEvaluacions = new ArrayList<>();
    @OneToMany(mappedBy = "cita")
    private List<ObservacionesIniciales> observacionesIniciales = new ArrayList<>();
    @OneToMany(mappedBy = "cita")
    private List<DiagnosticoHipotetico> diagnosticoHipoteticos = new ArrayList<>();
}
