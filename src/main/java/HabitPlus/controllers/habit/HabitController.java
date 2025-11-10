package HabitPlus.controllers.habit;

import HabitPlus.DTO.habit.HabitDTO;
import HabitPlus.service.habit.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits/v1")
public class HabitController {

    @Autowired
    private HabitService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HabitDTO create(HabitDTO habit){
        return service.create(habit);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HabitDTO update(@RequestBody HabitDTO habit){
        return service.update(habit);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HabitDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HabitDTO> findAll(){
        return service.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        service.delete(id);
    }

}
