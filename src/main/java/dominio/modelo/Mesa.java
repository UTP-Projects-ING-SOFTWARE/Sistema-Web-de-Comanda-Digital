package dominio.modelo;
import java.util.Objects;
/**
 *
 * @author inici4rsesi0n
 */
public class Mesa {
    private Long id;
    private String numero;
    private Zona zona;
    private EstadoMesa estado;
    protected Mesa() {}
    public Mesa(String numero, String nombreZona) {
        this(numero, new Zona(nombreZona));
    }
    public Mesa(String numero, Zona zona) {
        if (numero == null || numero.isBlank()) throw new IllegalArgumentException("Número no puede ser nulo/vacío");
        if (zona == null || zona.getNombre() == null || zona.getNombre().isBlank()) throw new IllegalArgumentException("Zona no puede ser nula/vacía");
        this.numero = numero;
        this.zona = zona;
        this.estado = EstadoMesa.LIBRE;
    }
    public void ocupar() {
        if (this.estado == EstadoMesa.OCUPADO) throw new IllegalStateException("La mesa ya está ocupada");
        this.estado = EstadoMesa.OCUPADO;
    }
    public void liberar() {
        if (this.estado == EstadoMesa.LIBRE) throw new IllegalStateException("La mesa ya está libre");
        this.estado = EstadoMesa.LIBRE;
    }
    public boolean estaLibre() { return this.estado == EstadoMesa.LIBRE; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public Zona getZona() { return zona; }
    public void setZona(Zona zona) { this.zona = zona; }
    public EstadoMesa getEstado() { return estado; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mesa mesa)) return false;
        return Objects.equals(numero, mesa.numero) && Objects.equals(zona, mesa.zona);
    }
    @Override
    public int hashCode() { return Objects.hash(numero, zona); }
}
