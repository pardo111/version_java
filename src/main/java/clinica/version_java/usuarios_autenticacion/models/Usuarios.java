package clinica.version_java.usuarios_autenticacion.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.enums.Estado;
import clinica.version_java.usuarios_autenticacion.models.enums.Puestos;
import clinica.version_java.usuarios_autenticacion.models.enums.Roles;
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

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
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


    public Usuarios(Usuarios usuario) {
        this.idUsuario = usuario.idUsuario;
        this.usuario = usuario.usuario;
        this.estado = usuario.estado;
        this.password = usuario.password;
        this.fechaCreacion = usuario.fechaCreacion;
        this.fechaActualizado = usuario.fechaActualizado;
        this.rol = usuario.rol;
        this.puesto = usuario.puesto;
        this.persona = usuario.persona;
    }


    
}
