package clinica.version_java.usuarios_autenticacion.DTO;

import clinica.version_java.usuarios_autenticacion.models.Usuarios;
import clinica.version_java.usuarios_autenticacion.models.enums.Puestos;
import clinica.version_java.usuarios_autenticacion.models.enums.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DTOUsuarios {
    private String usuario;
    private String password;
    private Roles  rol;
    private Puestos puesto;
    private int idPersona;

    public DTOUsuarios(Usuarios usuarios){
        this.usuario = usuarios.getUsuario();
        this.rol = usuarios.getRol();
        this.puesto = usuarios.getPuesto();
    }
}
