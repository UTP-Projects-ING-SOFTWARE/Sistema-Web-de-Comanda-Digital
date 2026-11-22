package presentacion.api;
import aplicacion.casosdeuso.GestionZonas;
import dominio.modelo.Zona;
import dominio.puerto.externo.LoggerPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import presentacion.api.dto.ZonaDTO;
import presentacion.api.dto.ZonaRequest;
import java.util.List;
/**
 *
 * @author inici4rsesi0n
 */
@RestController
@RequestMapping("/api/admin/zonas")
public class AdminZonaController {
    private final GestionZonas gestionZonas;
    private final LoggerPort logger;
    public AdminZonaController(GestionZonas gestionZonas, LoggerPort logger) {
        this.gestionZonas = gestionZonas;
        this.logger = logger;
    }
    @GetMapping
    public ResponseEntity<List<ZonaDTO>> listar() {
        logger.debug("[API] GET /api/admin/zonas");
        return ResponseEntity.ok(gestionZonas.listarTodas().stream().map(ZonaDTO::desde).toList());
    }
    @PostMapping
    public ResponseEntity<ZonaDTO> crear(@Valid @RequestBody ZonaRequest req) {
        logger.debug("[API] POST /api/admin/zonas - Payload: {}", req.nombre());
        Zona z = gestionZonas.agregar(req.nombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(ZonaDTO.desde(z));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ZonaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ZonaRequest req) {
        logger.debug("[API] PUT /api/admin/zonas/{} - Payload: {}", id, req.nombre());
        gestionZonas.actualizar(id, req.nombre());
        return ResponseEntity.ok(ZonaDTO.desde(gestionZonas.obtener(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.debug("[API] DELETE /api/admin/zonas/{}", id);
        gestionZonas.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
