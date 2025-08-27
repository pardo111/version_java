
package clinica.version_java.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.version_java.models.Citas;
import clinica.version_java.models.enums.Estado;


@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer>{
    Page<Citas> findByCodigoContaining(String codigo,Pageable pag);
    Page<Citas> findByCodigo(String codigo, Pageable pag);
    Page<Citas> findByPaciente(Integer paciente, Pageable pag);
    Page<Citas> findByEstado(Estado estado, Pageable pag);
    Page<Citas> findByFechaCitaCierreBetween(LocalDateTime fechaInicio, LocalDateTime fechaFinal, Pageable pag);
    Page<Citas> findByFechaCitaInicio(LocalDateTime fechaCitaCierre, Pageable pag);
    Page<Citas> findByFactura(String factura, Pageable pag);
    Page<Citas> findByFacturaContaining(String factura, Pageable pag);
    Page<Citas> findByPagadoTrue(Pageable pag);
    Page<Citas> findByPagadoFalse(Pageable pag);
    Page<Citas> findByMedico(Integer medico, Pageable pag);

}
