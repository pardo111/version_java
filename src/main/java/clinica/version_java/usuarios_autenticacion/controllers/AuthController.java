package clinica.version_java.usuarios_autenticacion.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.version_java.usuarios_autenticacion.DTO.request.LoginRequest;
import clinica.version_java.usuarios_autenticacion.DTO.response.LoginResponse;
import clinica.version_java.usuarios_autenticacion.util.JwtUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    LoginResponse bodyResponse = new LoginResponse();

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest bodyRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(bodyRequest.getUsername(), bodyRequest.getPassword()));
            bodyResponse.setToken(jwtUtil.generateToken(bodyRequest.getUsername()));
            bodyResponse.setUsername(bodyRequest.getUsername());
            bodyResponse.setMessage("Inicio de sesion exitoso");

            return ResponseEntity.status(HttpStatus.OK).body(bodyResponse);
        } catch (AuthenticationException e) {
            bodyResponse.setUsername(bodyRequest.getUsername());
            bodyResponse.setMessage("Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(bodyResponse);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody LoginRequest request) {
        String refreshToken = request.getToken();
        try {
            String username = jwtUtil.getUserNameFromToken(refreshToken);

            if (!jwtUtil.isTokenExpired(refreshToken)) {
                String newAccessToken = jwtUtil.generateToken(username);
                LoginResponse response = new LoginResponse();
                response.setUsername(username);
                response.setToken(newAccessToken);
                response.setMessage("Access token renovado");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


}
