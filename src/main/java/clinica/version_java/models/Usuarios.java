package clinica.version_java.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import clinica.version_java.models.enums.Estado;
import clinica.version_java.models.enums.Puestos;
import clinica.version_java.models.enums.Roles;
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

@Entity
@Table(name = "usuarios")
@Data
public class Usuarios {
    
    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    @Column(name = "usuario")
    private String usuario;
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;
    @Column(name = "password")
    private String password;
    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    @UpdateTimestamp
    private LocalDateTime fechaActualizado;
    @Enumerated(EnumType.STRING)
    private Roles rol;
    @Enumerated(EnumType.STRING)
    private Puestos puesto;


    @OneToOne
    @JoinColumn(name= "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

}
