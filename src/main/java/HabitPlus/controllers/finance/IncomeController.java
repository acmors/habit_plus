package HabitPlus.controllers.finance;

import HabitPlus.DTO.finance.IncomeDTO;
import HabitPlus.service.finance.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits/v1")
public class IncomeController {

    @Autowired
    private IncomeService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeDTO create(IncomeDTO income){
        return service.create(income);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeDTO update(@RequestBody IncomeDTO income){
        return service.update(income);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<IncomeDTO> findAll(){
        return service.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        service.delete(id);
    }

}
