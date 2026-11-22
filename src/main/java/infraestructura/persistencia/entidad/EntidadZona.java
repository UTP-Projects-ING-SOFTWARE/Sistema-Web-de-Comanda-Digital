package infraestructura.persistencia.entidad;
import jakarta.persistence.*;
/**
 *
 * @author inici4rsesi0n
 */
@Entity
@Table(name="zonas")
public class EntidadZona {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=100)
    private String nombre;
    public EntidadZona() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
