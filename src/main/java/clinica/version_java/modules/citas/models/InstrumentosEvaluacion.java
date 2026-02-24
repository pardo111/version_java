package clinica.version_java.modules.citas.models;

import clinica.version_java.modules.citas.models.enums.PruebasPsicometricas;
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

@Entity
@Data
@Table(name= "instrumentos_evaluacion")
@NoArgsConstructor
public class InstrumentosEvaluacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_instrumento")
    private int id;
    @Column(name="prueba_psicometrica_aplicada")
    @Enumerated(EnumType.STRING)
    private PruebasPsicometricas pruebaPsicometricaAplicada;
    @Column(name="cuestionarios_escala")
    private String cuestionariosEscala;
    @Column(name="resultados")
    private String resultados;
    @Column(name="estado")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;

    
    @ManyToOne
    @JoinColumn(name = "id_cita", referencedColumnName = "id_cita")
    private Citas cita; 
    
}
