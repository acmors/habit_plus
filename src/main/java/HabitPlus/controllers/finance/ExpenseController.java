package HabitPlus.controllers.finance;

import HabitPlus.DTO.finance.ExpenseDTO;
import HabitPlus.service.finance.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ExpenseDTO create(ExpenseDTO expense){
        return service.create(expense);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ExpenseDTO update(@RequestBody ExpenseDTO expense){
        return service.update(expense);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExpenseDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExpenseDTO> findAll(){
        return service.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
