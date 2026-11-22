package infraestructura.persistencia.adaptador;
import dominio.modelo.EstadoMesa;
import dominio.modelo.Mesa;
import dominio.puerto.repositorio.RepositorioMesa;
import infraestructura.persistencia.entidad.EntidadMesa;
import infraestructura.persistencia.entidad.EntidadZona;
import infraestructura.persistencia.mapper.MesaMapper;
import infraestructura.persistencia.repositorioJPA.RepositorioMesaJPA;
import infraestructura.persistencia.repositorioJPA.RepositorioZonaJPA;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author inici4rsesi0n
 */
@Repository
public class AdaptadorRepositorioMesa implements RepositorioMesa {
    private final RepositorioMesaJPA jpa;
    private final RepositorioZonaJPA jpaZona;
    public AdaptadorRepositorioMesa(RepositorioMesaJPA jpa, RepositorioZonaJPA jpaZona) {
        this.jpa = jpa;
        this.jpaZona = jpaZona;
    }
    @Override public void agregar(Mesa m) {
        EntidadZona zona = jpaZona.findById(m.getZona().getId()).orElseThrow(() -> new IllegalStateException("Zona no encontrada"));
        EntidadMesa guardada = jpa.save(MesaMapper.aEntidad(m, zona));
        m.setId(guardada.getId());
    }
    @Override public void actualizar(Mesa m) {
        EntidadZona zona = jpaZona.findById(m.getZona().getId()).orElseThrow(() -> new IllegalStateException("Zona no encontrada"));
        jpa.save(MesaMapper.aEntidad(m, zona));
    }
    @Override public Optional<Mesa> buscarPorId(Long id) { return jpa.findById(id).map(MesaMapper::aDominio); }
    @Override public List<Mesa> listarTodos() { return jpa.findAll().stream().map(MesaMapper::aDominio).toList(); }
    @Override public void eliminar(Mesa mesa) { jpa.deleteById(mesa.getId()); }
    @Override public Optional<Mesa> buscarPorNumero(String numero) { return jpa.findByNumero(numero).map(MesaMapper::aDominio); }
    @Override public List<Mesa> listarLibres() { return jpa.findByEstado(EstadoMesa.LIBRE).stream().map(MesaMapper::aDominio).toList(); }
    @Override public List<Mesa> listarOcupadas() { return jpa.findByEstado(EstadoMesa.OCUPADO).stream().map(MesaMapper::aDominio).toList(); }
    @Override public List<Mesa> buscarPorNumeroContiene(String texto) { return jpa.findByNumeroContainingIgnoreCase(texto).stream().map(MesaMapper::aDominio).toList(); }
}
