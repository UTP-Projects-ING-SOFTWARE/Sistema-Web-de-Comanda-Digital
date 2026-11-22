package dominio.modelo;
import java.util.Objects;
/**
 *
 * @author inici4rsesi0n
 */
public class Zona {
    private Long id;
    private String nombre;
    public Zona(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre no puede ser nulo/vacío");
        this.nombre = nombre;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zona zona)) return false;
        return Objects.equals(nombre, zona.nombre);
    }
    @Override
    public int hashCode() { return Objects.hash(nombre); }
}
