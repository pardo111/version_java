
package clinica.version_java.modules.citas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.modules.citas.models.Citas;
 

@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer>{

}
