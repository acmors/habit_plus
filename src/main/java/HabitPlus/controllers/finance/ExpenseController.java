package HabitPlus.controllers.finance;

import HabitPlus.DTO.finance.ExpenseRequest;
import HabitPlus.DTO.finance.ExpenseResponse;
import HabitPlus.service.finance.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense/v1")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseResponse> create(@RequestBody ExpenseRequest request){
        ExpenseResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseResponse> update(@PathVariable("id") Long id, @RequestBody ExpenseRequest request){
        ExpenseResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseResponse> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExpenseResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
