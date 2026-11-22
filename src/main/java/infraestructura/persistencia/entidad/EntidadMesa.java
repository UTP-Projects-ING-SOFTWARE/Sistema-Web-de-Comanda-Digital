package infraestructura.persistencia.entidad;
import dominio.modelo.EstadoMesa;
import jakarta.persistence.*;
/**
 *
 * @author inici4rsesi0n
 */
@Entity
@Table(name="mesas", uniqueConstraints = @UniqueConstraint(columnNames = {"numero", "zona_id"}))
public class EntidadMesa {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=20)
    private String numero;
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="zona_id", nullable=false)
    private EntidadZona zona;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private EstadoMesa estado;
    public EntidadMesa() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public EntidadZona getZona() { return zona; }
    public void setZona(EntidadZona zona) { this.zona = zona; }
    public EstadoMesa getEstado() { return estado; }
    public void setEstado(EstadoMesa estado) { this.estado = estado; }
}
