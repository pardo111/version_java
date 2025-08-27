package clinica.version_java.models;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Data
@Table(name="correo_persona")
public class CorreoPersona {
    
    @Id
    @Column(name="id_correo_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCorreoPersona;
    @Column(name="correo", nullable = false, unique = true)
    private String correo;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;    


    @ManyToOne
    @JoinColumn(name="persona_id", referencedColumnName = "id_persona")
    private Persona persona;


    public CorreoPersona(String correo, Persona persona) {
        this.correo = correo;
        this.persona = persona;
    }




}
