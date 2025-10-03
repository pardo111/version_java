package clinica.version_java.usuarios_autenticacion.repositories;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.personas.models.Persona;
import clinica.version_java.personas.models.enums.Estado;
import clinica.version_java.usuarios_autenticacion.models.Usuarios;


@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
    Optional<Usuarios> findByUsuario(String usuario);   
    boolean existsByPersona(Persona persona);
    Optional <Usuarios> findByPersona(Persona persona);
    Page<Usuarios> findByEstado(Estado estado, Pageable pageable);
}
