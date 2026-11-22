package presentacion.api.dto;
import jakarta.validation.constraints.NotBlank;
/**
 *
 * @author inici4rsesi0n
 */
public record ZonaRequest(@NotBlank(message = "El nombre es obligatorio") String nombre) {}
