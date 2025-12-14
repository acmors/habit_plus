package HabitPlus.controllers.finance;

import HabitPlus.DTO.finance.ExpenseRequest;
import HabitPlus.DTO.finance.ExpenseResponse;
import HabitPlus.config.SecurityConfig;
import HabitPlus.service.finance.ExpenseService;
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
@RequestMapping("/api/v1/expense")
@Tag(name = "Expense", description = "Controlador para criar, editar, buscar e deletar Despesas.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma despesa para um usuário ", description = "Método para criar uma despesa para um usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<ExpenseResponse> create(@RequestBody ExpenseRequest request){
        ExpenseResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Edita uma despesa para um usuário ", description = "Método para editar uma despesa para um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Despesa editada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Despesa não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<ExpenseResponse> update(@PathVariable("id") Long id, @RequestBody ExpenseRequest request){
        ExpenseResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma despesa para um usuário ", description = "Método para buscar uma despesa para um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Despesa encontada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Despesa não encontada.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<ExpenseResponse> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca todas as despess de um usuário ", description = "Método para buscar todas as despesas de um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Despesas encontradas com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<List<ExpenseResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta uma despesa para um usuário ", description = "Método para deletar uma despesa para um usuário autenticado")
    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
    @ApiResponse(responseCode = "404", description = "Despesa não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
