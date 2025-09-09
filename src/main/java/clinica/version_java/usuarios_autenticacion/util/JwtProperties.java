package clinica.version_java.usuarios_autenticacion.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String clave;
    private long expira;

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public long getExpira() { return expira; }
    public void setExpira(long expira) { this.expira = expira; }
}
