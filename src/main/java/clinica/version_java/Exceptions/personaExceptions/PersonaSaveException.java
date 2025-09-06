package clinica.version_java.Exceptions.personaExceptions;

public class PersonaSaveException extends RuntimeException {
    public PersonaSaveException(String mensaje){
        super(mensaje);
    }
}
