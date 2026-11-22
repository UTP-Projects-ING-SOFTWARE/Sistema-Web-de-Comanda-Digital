package aplicacion.casosdeuso;
import dominio.excepcion.RecursoNoEncontradoException;
import dominio.modelo.Mesa;
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
public class GestionMesas {
    private final RepositorioMesa repositorioMesa;
    private final RepositorioZona repositorioZona;
    private final LoggerPort logger;
    public GestionMesas(RepositorioMesa repositorioMesa, RepositorioZona repositorioZona, LoggerPort logger) {
        this.repositorioMesa = repositorioMesa;
        this.repositorioZona = repositorioZona;
        this.logger = logger;
    }
    public Mesa agregar(String numero, Long zonaId) {
        if (repositorioMesa.buscarPorNumero(numero).isPresent()) throw new IllegalStateException("Ya existe una mesa con el número " + numero);
        Zona zona = repositorioZona.buscarPorId(zonaId).orElseThrow(() -> new RecursoNoEncontradoException("Zona no encontrada"));
        Mesa mesa = new Mesa(numero, zona);
        repositorioMesa.agregar(mesa);
        logger.info("Mesa creada: {}", mesa);
        return mesa;
    }
    public void actualizar(Long id, String numero, Long zonaId) {
        Mesa mesa = obtener(id);
        Zona zona = repositorioZona.buscarPorId(zonaId).orElseThrow(() -> new RecursoNoEncontradoException("Zona no encontrada"));
        if (numero != null && !numero.isBlank() && !numero.equals(mesa.getNumero())) {
            if (repositorioMesa.buscarPorNumero(numero).isPresent()) throw new IllegalStateException("Ya existe una mesa con el número " + numero);
            mesa.setNumero(numero);
        }
        mesa.setZona(zona);
        repositorioMesa.actualizar(mesa);
        logger.info("Mesa actualizada: {}", mesa);
    }
    public void eliminar(Long id) {
        Mesa mesa = obtener(id);
        if (!mesa.estaLibre()) throw new IllegalStateException("No se puede eliminar una mesa ocupada");
        repositorioMesa.eliminar(mesa);
        logger.info("Mesa eliminada: {}", mesa);
    }
    public void ocupar(Long id) {
        Mesa mesa = obtener(id);
        mesa.ocupar();
        repositorioMesa.actualizar(mesa);
        logger.info("Mesa ocupada: {}", mesa);
    }
    public void liberar(Long id) {
        Mesa mesa = obtener(id);
        mesa.liberar();
        repositorioMesa.actualizar(mesa);
        logger.info("Mesa liberada: {}", mesa);
    }
    public Mesa obtener(Long id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");
        return repositorioMesa.buscarPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("Mesa no encontrada"));
    }
    public List<Mesa> listarTodas() { return repositorioMesa.listarTodos(); }
    public List<Mesa> listarLibres() { return repositorioMesa.listarLibres(); }
    public List<Mesa> listarOcupadas() { return repositorioMesa.listarOcupadas(); }
    public List<Mesa> buscar(String texto) {
        if (texto == null || texto.isBlank()) return repositorioMesa.listarTodos();
        return repositorioMesa.buscarPorNumeroContiene(texto);
    }
}
