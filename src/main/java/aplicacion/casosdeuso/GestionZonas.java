package aplicacion.casosdeuso;
import dominio.excepcion.RecursoNoEncontradoException;
import dominio.modelo.Zona;
import dominio.puerto.externo.LoggerPort;
import dominio.puerto.repositorio.RepositorioMesa;
import dominio.puerto.repositorio.RepositorioZona;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 *
 * @author inici4rsesi0n
 */
@Service
public class GestionZonas {
    private final RepositorioZona repositorioZona;
    private final RepositorioMesa repositorioMesa;
    private final LoggerPort logger;
    public GestionZonas(RepositorioZona repositorioZona, RepositorioMesa repositorioMesa, LoggerPort logger) {
        this.repositorioZona = repositorioZona;
        this.repositorioMesa = repositorioMesa;
        this.logger = logger;
    }
    public Zona agregar(String nombre) {
        if (repositorioZona.buscarPorNombre(nombre).isPresent()) throw new IllegalStateException("Ya existe una zona con el nombre " + nombre);
        Zona zona = new Zona(nombre);
        repositorioZona.agregar(zona);
        logger.info("Zona creada: {}", zona);
        return zona;
    }
    public void actualizar(Long id, String nombre) {
        Zona zona = obtener(id);
        if (!zona.getNombre().equals(nombre) && repositorioZona.buscarPorNombre(nombre).isPresent()) {
            throw new IllegalStateException("Ya existe una zona con el nombre " + nombre);
        }
        zona.setNombre(nombre);
        repositorioZona.actualizar(zona);
        logger.info("Zona actualizada: {}", zona);
    }
    public void eliminar(Long id) {
        Zona zona = obtener(id);
        long mesasAsociadas = repositorioMesa.listarTodos().stream().filter(m -> m.getZona().getId().equals(id)).count();
        if (mesasAsociadas > 0) {
            logger.warn("Intento de eliminar zona {} con {} mesas asociadas", zona.getNombre(), mesasAsociadas);
            throw new IllegalStateException("No se puede eliminar la zona '" + zona.getNombre() + "' porque tiene " + mesasAsociadas + " mesas asignadas.");
        }
        repositorioZona.eliminar(zona);
        logger.info("Zona eliminada: {}", zona);
    }
    public Zona obtener(Long id) {
        return repositorioZona.buscarPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("Zona no encontrada"));
    }
    public List<Zona> listarTodas() { return repositorioZona.listarTodos(); }
}
