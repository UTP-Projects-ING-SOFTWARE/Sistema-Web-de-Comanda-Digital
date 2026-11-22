package presentacion.api;
import aplicacion.casosdeuso.GestionMesas;
import dominio.modelo.EstadoMesa;
import dominio.modelo.Mesa;
import dominio.puerto.externo.LoggerPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import presentacion.api.dto.MesaDTO;
import presentacion.api.dto.MesaRequest;
import java.util.List;
/**
 *
 * @author inici4rsesi0n
 */
@RestController
@RequestMapping("/api/admin/mesas")
public class AdminMesaController {
    private final GestionMesas gestionMesas;
    private final LoggerPort logger;
    public AdminMesaController(GestionMesas gestionMesas, LoggerPort logger) {
        this.gestionMesas = gestionMesas;
        this.logger = logger;
    }
    @GetMapping
    public ResponseEntity<List<MesaDTO>> listar(@RequestParam(required=false) EstadoMesa estado) {
        logger.debug("[API] GET /api/admin/mesas - Filtro estado: {}", estado);
        List<Mesa> mesas;
        if (estado == EstadoMesa.LIBRE) mesas = gestionMesas.listarLibres();
        else if (estado == EstadoMesa.OCUPADO) mesas = gestionMesas.listarOcupadas();
        else mesas = gestionMesas.listarTodas();
        return ResponseEntity.ok(mesas.stream().map(MesaDTO::desde).toList());
    }
    @PostMapping
    public ResponseEntity<MesaDTO> crear(@Valid @RequestBody MesaRequest peticion) {
        logger.debug("[API] POST /api/admin/mesas - Payload: numero={}, zonaId={}", peticion.numero(), peticion.zonaId());
        Mesa mesa = gestionMesas.agregar(peticion.numero(), peticion.zonaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(MesaDTO.desde(mesa));
    }
    @PutMapping("/{id}")
    public ResponseEntity<MesaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MesaRequest peticion) {
        logger.debug("[API] PUT /api/admin/mesas/{} - Payload: numero={}, zonaId={}", id, peticion.numero(), peticion.zonaId());
        gestionMesas.actualizar(id, peticion.numero(), peticion.zonaId());
        return ResponseEntity.ok(MesaDTO.desde(gestionMesas.obtener(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logger.debug("[API] DELETE /api/admin/mesas/{}", id);
        gestionMesas.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/ocupar")
    public ResponseEntity<MesaDTO> ocupar(@PathVariable Long id) {
        logger.debug("[API] PATCH /api/admin/mesas/{}/ocupar", id);
        gestionMesas.ocupar(id);
        return ResponseEntity.ok(MesaDTO.desde(gestionMesas.obtener(id)));
    }
    @PatchMapping("/{id}/liberar")
    public ResponseEntity<MesaDTO> liberar(@PathVariable Long id) {
        logger.debug("[API] PATCH /api/admin/mesas/{}/liberar", id);
        gestionMesas.liberar(id);
        return ResponseEntity.ok(MesaDTO.desde(gestionMesas.obtener(id)));
    }
}
