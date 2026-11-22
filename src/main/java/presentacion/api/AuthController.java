package presentacion.api;
import dominio.modelo.Usuario;
import dominio.puerto.externo.LoggerPort;
import infraestructura.seguridad.UsuarioDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import presentacion.api.dto.ErrorResponse;
import presentacion.api.dto.LoginRequest;
import presentacion.api.dto.LoginResponse;
/**
 *
 * @author inici4rsesi0n
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController{
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final LoggerPort logger;
    public AuthController(AuthenticationManager authenticationManager,
                          SecurityContextRepository securityContextRepository,
                          LoggerPort logger){
        this.authenticationManager=authenticationManager;
        this.securityContextRepository=securityContextRepository;
        this.logger=logger;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest peticion,
                                                HttpServletRequest request,
                                                HttpServletResponse response){
        logger.debug("Petición REST recibida para login: {}", peticion.codigo());
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(peticion.codigo(), peticion.password()));
        SecurityContext context=SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        UsuarioDetails details=(UsuarioDetails)auth.getPrincipal();
        Usuario usuario=details.getUsuario();
        logger.debug("Contexto de seguridad guardado para: {}", peticion.codigo());
        return ResponseEntity.ok(LoginResponse.desde(usuario, "Login exitoso"));
    }
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UsuarioDetails details){
        if(details==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponse.de(HttpStatus.UNAUTHORIZED, "No autenticado", "/api/auth/me"));
        return ResponseEntity.ok(LoginResponse.desde(details.getUsuario(), "Sesión activa"));
    }
}
