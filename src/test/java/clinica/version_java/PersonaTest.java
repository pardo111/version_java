package clinica.version_java;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import clinica.version_java.repositories.PersonaRepository;
import clinica.version_java.services.PersonaService;

@SpringBootTest
public class PersonaTest {
    
    @MockBean
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaService personaService;


    @Test 
    public void testCrearPersonaCompleta() {
        
    }
    
}
