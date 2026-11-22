package presentacion.api;
import aplicacion.casosdeuso.GestionZonas;
import boot.RestobarApp;
import dominio.puerto.repositorio.RepositorioZona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 *
 * @author inici4rsesi0n
 */
@SpringBootTest(classes = RestobarApp.class)
@AutoConfigureMockMvc
@Transactional
@WithMockUser(roles = "ADMINISTRADOR")
class AdminMesaControllerIT {
    @Autowired MockMvc mockMvc;
    @Autowired GestionZonas gestionZonas;
    @Autowired RepositorioZona repositorioZona;
    private Long zonaId;
    @BeforeEach
    void seed() {
        if (repositorioZona.buscarPorNombre("Salón Principal").isEmpty()) gestionZonas.agregar("Salón Principal");
        zonaId = repositorioZona.buscarPorNombre("Salón Principal").get().getId();
    }
    @Test
    void crearMesaDevuelve201() throws Exception {
        mockMvc.perform(post("/api/admin/mesas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numero\":\"M3\",\"zonaId\":" + zonaId + "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero").value("M3"))
                .andExpect(jsonPath("$.estado").value("LIBRE"));
    }
}
