package clinica.version_java.citas.models;

import clinica.version_java.citas.models.enums.EstadoAnimo;
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

@Entity
@Data
@Table(name = "estado_mental")
@NoArgsConstructor
public class EstadoMental {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_estado_mental")
    private int id;
    @Column(name="apariencia_general", length = 200)
    private String aparienciaGeneral;
    @Column(name = "conducta", length =350)
    private String conducta;
    @Column(name= "estado_animo")
    @Enumerated(EnumType.STRING)
    private EstadoAnimo estadoAnimo;
    @Column(name = "proceso_pensamiento")
    private String procesoPensamiento;
    @Column(name = "estado")
    private Estado estado = Estado.ACTIVO;


    @ManyToOne
    @JoinColumn(name="id_cita", referencedColumnName = "id_cita")   
    private Citas cita;
}
