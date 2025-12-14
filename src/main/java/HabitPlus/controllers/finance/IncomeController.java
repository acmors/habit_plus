package HabitPlus.controllers.finance;

import HabitPlus.DTO.finance.IncomeRequest;
import HabitPlus.DTO.finance.IncomeResponse;
import HabitPlus.config.SecurityConfig;
import HabitPlus.service.finance.IncomeService;
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
@RequestMapping("/api/v1/income")
@Tag(name = "Income", description = "Controlador para criar, editar, buscar e deletar Receitas.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class IncomeController {

    @Autowired
    private IncomeService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma receita para um usuário ", description = "Método para criar uma receita para um usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Receita criada com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.") public ResponseEntity<IncomeResponse> create(@RequestBody IncomeRequest request){
        IncomeResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Edita uma receita para um usuário ", description = "Método para editar uma receita para um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Receita editada com sucesso.")
    @ApiResponse(responseCode = "204", description = "Receita não encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<IncomeResponse> update(@PathVariable("id")Long id, @RequestBody IncomeRequest request){
        IncomeResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma receita para um usuário ", description = "Método para buscar uma receita para um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Receita encontada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Receita não encontada.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<IncomeResponse> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca todas as receitas de um usuário ", description = "Método para buscar todas as receitas de um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Receitas encontradas com sucesso.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<List<IncomeResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta uma receita para um usuário ", description = "Método para deletar uma receita para um usuário autenticado")
    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
    @ApiResponse(responseCode = "404", description = "Receita não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
