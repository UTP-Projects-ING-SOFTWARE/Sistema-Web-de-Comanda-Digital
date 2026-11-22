package presentacion.api.dto;
import dominio.modelo.Zona;
/**
 *
 * @author inici4rsesi0n
 */
public record ZonaDTO(Long id, String nombre) {
    public static ZonaDTO desde(Zona z) { return new ZonaDTO(z.getId(), z.getNombre()); }
}
