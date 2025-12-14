package HabitPlus.auth;

import HabitPlus.config.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Controlador para criar usuário e autenticar no sistema.")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(summary = "Cria um novo usuário", description = "Método para criar um novo usuário.")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza a autenticação do usuário", description = "Método para autenticar o usuário no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
