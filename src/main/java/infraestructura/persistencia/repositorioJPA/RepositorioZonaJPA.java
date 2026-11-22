package infraestructura.persistencia.repositorioJPA;
import infraestructura.persistencia.entidad.EntidadZona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 *
 * @author inici4rsesi0n
 */
public interface RepositorioZonaJPA extends JpaRepository<EntidadZona, Long> {
    Optional<EntidadZona> findByNombre(String nombre);
}
