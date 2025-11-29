package HabitPlus.controllers.finance;

import HabitPlus.DTO.finance.IncomeRequest;
import HabitPlus.DTO.finance.IncomeResponse;
import HabitPlus.service.finance.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income/v1")
public class IncomeController {

    @Autowired
    private IncomeService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IncomeResponse> create(@RequestBody IncomeRequest request){
        IncomeResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IncomeResponse> update(@PathVariable("id")Long id, @RequestBody IncomeRequest request){
        IncomeResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IncomeResponse> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IncomeResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
