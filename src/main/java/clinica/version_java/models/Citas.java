package clinica.version_java.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "citas")
@Data
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


    @ManyToOne
    @JoinColumn(name="id_paciente", referencedColumnName = "id_persona")
    private Persona paciente;
    @ManyToOne
    @JoinColumn(name="id_medico", referencedColumnName = "id_persona")
    private Persona medico;
}
