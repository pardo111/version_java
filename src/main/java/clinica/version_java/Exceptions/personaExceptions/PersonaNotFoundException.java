package clinica.version_java.Exceptions.personaExceptions;

public class PersonaNotFoundException extends RuntimeException{
    
    public PersonaNotFoundException(String mensaje){
        super(mensaje);
    }
}
