package clinica.version_java.usuarios_autenticacion.DTO.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password; 
    private String token;
}
