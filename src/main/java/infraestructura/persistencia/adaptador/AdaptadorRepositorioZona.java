package infraestructura.persistencia.adaptador;
import dominio.modelo.Zona;
import dominio.puerto.repositorio.RepositorioZona;
import infraestructura.persistencia.entidad.EntidadZona;
import infraestructura.persistencia.mapper.ZonaMapper;
import infraestructura.persistencia.repositorioJPA.RepositorioZonaJPA;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author inici4rsesi0n
 */
@Repository
public class AdaptadorRepositorioZona implements RepositorioZona {
    private final RepositorioZonaJPA jpa;
    public AdaptadorRepositorioZona(RepositorioZonaJPA jpa) { this.jpa = jpa; }
    @Override public void agregar(Zona z) { EntidadZona e = jpa.save(ZonaMapper.aEntidad(z)); z.setId(e.getId()); }
    @Override public void actualizar(Zona z) { jpa.save(ZonaMapper.aEntidad(z)); }
    @Override public Optional<Zona> buscarPorId(Long id) { return jpa.findById(id).map(ZonaMapper::aDominio); }
    @Override public List<Zona> listarTodos() { return jpa.findAll().stream().map(ZonaMapper::aDominio).toList(); }
    @Override public void eliminar(Zona z) { jpa.deleteById(z.getId()); }
    @Override public Optional<Zona> buscarPorNombre(String nombre) { return jpa.findByNombre(nombre).map(ZonaMapper::aDominio); }
}
