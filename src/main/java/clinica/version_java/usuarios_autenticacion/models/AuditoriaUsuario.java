package clinica.version_java.usuarios_autenticacion.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import clinica.version_java.usuarios_autenticacion.models.enums.EventoUsuario;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auditoria_usuario")
@Data
@NoArgsConstructor
public class AuditoriaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "evento")
    @Enumerated(EnumType.STRING)
    private EventoUsuario evento;

    @Column(name = "ip")
    private String ip;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    public AuditoriaUsuario(String ip, EventoUsuario evento, int idUsuario) {
        this.ip = ip;
        this.evento = evento;
        this.idUsuario = idUsuario;
    }

}
