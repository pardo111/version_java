
package clinica.version_java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.Citas;
 

@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer>{

}
