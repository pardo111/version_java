package clinica.version_java.citas.models;

import clinica.version_java.personas.models.enums.Estado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "observaciones_iniciales")
@NoArgsConstructor
public class ObservacionesIniciales {
    


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_observacion_inicial")
    private int id;
    @Column(name="observaciones_iniciales",length = 200)
    private String observacionesIniciales;
    @Column(name="comportamiento", length = 100)
    private String comportamiento;
    @Column(name="comentarios", length = 400)
    private String comentarios;
    @Column(name="estado")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;


    @ManyToOne
    @JoinColumn(name = "id_cita", referencedColumnName = "id_cita")
    private Citas cita; 
}
