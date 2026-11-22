package presentacion.api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
/**
 *
 * @author inici4rsesi0n
 */
public record MesaRequest(
    @NotBlank(message = "El número no puede ser nulo/vacío") String numero,
    @NotNull(message = "La zona no puede ser nula") Long zonaId
) {}
