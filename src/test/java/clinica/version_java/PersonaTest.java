package clinica.version_java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import clinica.version_java.DTOs.DTOPersonaBase;
import clinica.version_java.DTOs.DTOPersona;
import clinica.version_java.models.Persona;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonaTest {

    @LocalServerPort
    private int port;

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/persona";
    }

    @Test
    void deberiaCrearUnaPersonaSinProblemasNiDuplicados() {

        String jsonPersona = """
                                {
                    "nombres": "ano",
                    "apellidos": "pacheco",
                    "fechaNacimiento": "2000-12-12",
                    "direccion": "direcccion random",
                    "sexo": "MASCULINO",
                    "dui": "0000000-1",
                    "tipoPersona": "PACIENTE",
                    "contactosEmergencia": [
                        {
                            "nombres": "pedro",
                            "apellidos": "pacheco",
                            "relacion": "PAREJA",
                            "fechaNacimiento": "2000-12-12",
                            "direccion": "direcccion random",
                            "sexo": "MASCULINO",
                            "dui": "0000002-1",
                            "tipoPersona": "PACIENTE",
                            "correos": [
                                "corre2@gmail.com",
                                "correo2@mail.com"
                            ],
                            "telefonos": [
                                "02",
                                "2"
                            ]
                        }
                    ],
                    "correos": [
                        "correa@gmail.com",
                        "correo@mail.com"
                    ],
                    "telefonos": [
                        "10",
                        "1"
                    ],
                    "antecedentesFamiliares": [
                        {
                            "nombres": "pepa",
                            "apellidos": "pacheco",
                            "antecedente": "antecedente random",
                            "fechaNacimiento": "2000-12-12",
                            "direccion": "direcccion random",
                            "sexo": "MASCULINO",
                            "dui": "0002000-1",
                            "tipoPersona": "PACIENTE",
                            "correos": [
                                "corre3@gmail.com",
                                "correo3@mail.com"
                            ],
                            "telefonos": [
                                "03",
                                "3"
                            ]
                        },
                        {
                            "nombres": "pepe",
                            "apellidos": "pacheco",
                            "antecedentes": "antecedente random",
                            "fechaNacimiento": "2000-12-12",
                            "direccion": "direcccion random",
                            "sexo": "MASCULINO",
                            "dui": "0001000-1",
                            "tipoPersona": "PACIENTE",
                            "correos": [
                                "corre4@gmail.com",
                                "corre43@mail.com"
                            ],
                            "telefonos": [
                                "4",
                                "40"
                            ]
                        }
                    ]
                }
                                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(jsonPersona, headers);

        ResponseEntity<DTOPersona> response = restTemplate.postForEntity(baseUrl + "/create", request,
                DTOPersona.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void debeRetornarPaginacionDTOPersonaBase() {
        String json = """
                {
                    "tipoPersona":"PACIENTE"
                }
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/pages?tipoPersona=PACIENTE",
                HttpMethod.GET,
                request,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }
}
