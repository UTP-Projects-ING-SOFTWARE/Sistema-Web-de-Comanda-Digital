package infraestructura.persistencia.mapper;
import dominio.modelo.Zona;
import infraestructura.persistencia.entidad.EntidadZona;
/**
 *
 * @author inici4rsesi0n
 */
public class ZonaMapper {
    private ZonaMapper() {}
    public static Zona aDominio(EntidadZona e) {
        if (e == null) return null;
        Zona z = new Zona(e.getNombre());
        z.setId(e.getId());
        return z;
    }
    public static EntidadZona aEntidad(Zona z) {
        if (z == null) return null;
        EntidadZona e = new EntidadZona();
        e.setId(z.getId());
        e.setNombre(z.getNombre());
        return e;
    }
}
