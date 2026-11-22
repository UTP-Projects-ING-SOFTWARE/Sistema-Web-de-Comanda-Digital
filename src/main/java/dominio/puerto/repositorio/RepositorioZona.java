package dominio.puerto.repositorio;
import dominio.modelo.Zona;
import java.util.Optional;
/**
 *
 * @author inici4rsesi0n
 */
public interface RepositorioZona extends Repositorio<Zona> {
    Optional<Zona> buscarPorNombre(String nombre);
}
