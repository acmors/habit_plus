package HabitPlus.service.habit;
import HabitPlus.DTO.habit.HabitDTO;
import static HabitPlus.mapper.habit.HabitMapper.parseObject;
import static HabitPlus.mapper.habit.HabitMapper.parseListHabits;

import HabitPlus.controllers.habit.HabitController;
import HabitPlus.exceptions.BadRequestException;
import HabitPlus.exceptions.ConflictException;
import HabitPlus.exceptions.ObjectNotFoundException;
import HabitPlus.model.habit.HabitEntity;
import HabitPlus.repository.habit.HabitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {

    @Autowired
    private HabitRepository repository;

    private Logger logger = LoggerFactory.getLogger(HabitService.class.getName());

    public HabitDTO create(HabitDTO habit){

        logger.info("Creating a Habit!");
        if (habit.getName() == null || habit.getName().isBlank()) {
            throw new BadRequestException("Habit name cannot be empty");
        }

        if (repository.existsByName(habit.getName())) {
            throw new ConflictException("Habit already exists");
        }

        var entity = parseObject(habit, HabitEntity.class);
        var dto = parseObject(repository.save(entity), HabitDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public HabitDTO update(HabitDTO habit){

        logger.info("Updating a Habit!");

        if (habit.getName() == null || habit.getName().isBlank()) {
            throw new BadRequestException("Habit name cannot be empty");
        }

        HabitEntity entity = repository.findById(habit.getId())
                .orElseThrow(() -> new ObjectNotFoundException("No records found"));
        entity.setName(habit.getName());
        entity.setDescription(habit.getDescription());
        entity.setPriority(habit.getPriority());
        entity.setDate(habit.getDate());
        var dto = parseObject(repository.save(entity), HabitDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public HabitDTO findById(Long id){

        logger.info("Finding a Habit!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No records found."));
        var dto = parseObject(entity, HabitDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public List<HabitDTO> findAll(){

        logger.info("Finding all Habits!");

        List<HabitDTO> habits = new ArrayList<HabitDTO>();
        var habitsDTO = parseListHabits(repository.findAll(), HabitDTO.class);
        habitsDTO.forEach(this::addHateoasLinks);
        return habitsDTO;
    }

    public void delete(Long id){

        logger.info("Deleting a Habit!");

        HabitEntity entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No records found."));

        repository.delete(entity);
    }

    private void addHateoasLinks(HabitDTO dto) {
        dto.add(linkTo(methodOn(HabitController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(HabitController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(HabitController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(HabitController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(HabitController.class).update(dto)).withRel("update").withType("PUT"));
    }



}
