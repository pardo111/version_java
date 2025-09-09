
package clinica.version_java.usuarios_autenticacion.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;
    
    private String claveSecreta = jwtProperties.getClave();
    private long expiracion = jwtProperties.getExpira();

    public String generateToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiracion))
                .signWith(SignatureAlgorithm.HS256, claveSecreta)
                .compact();
    }

    public String getUserNameFromToken (String token){
        return Jwts.parser()
                .setSigningKey(claveSecreta)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
