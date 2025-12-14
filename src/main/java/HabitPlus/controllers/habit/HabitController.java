package HabitPlus.controllers.habit;

import HabitPlus.DTO.habit.HabitRequest;
import HabitPlus.DTO.habit.HabitResponse;
import HabitPlus.config.SecurityConfig;
import HabitPlus.service.habit.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habit")
@Tag(name = "Habit", description = "Controlador para criar, editar, buscar e deletar Hábitos.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class HabitController {

    @Autowired
    private HabitService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um hábito para um usuário ", description = "Método para criar um hábito para um usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Hábito criado com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<HabitResponse> create(@RequestBody HabitRequest request){
        HabitResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Edita um hábito para um usuário ", description = "Método para editar um hábito para um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Hábito editado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Hábito não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<HabitResponse> update(@PathVariable("id")Long id, @RequestBody HabitRequest request){
        HabitResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca um hábito para um usuário ", description = "Método para buscar um hábito para um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Hábito encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Hábito não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<HabitResponse>  findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca todos os hábitos de um usuário", description = "Método para buscar todos os hábito de um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Hábitos encontrado com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<List<HabitResponse>>  findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta um hábito para um usuário ", description = "Método para deletar um hábito para um usuário autenticado")
    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
    @ApiResponse(responseCode = "404", description = "Hábito não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
