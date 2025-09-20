package clinica.version_java.usuarios_autenticacion.DTO.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String message;
    
}