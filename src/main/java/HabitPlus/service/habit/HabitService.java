package HabitPlus.service.habit;
import HabitPlus.DTO.habit.HabitRequest;
import HabitPlus.DTO.habit.HabitResponse;
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

    public HabitResponse create(HabitRequest request){
        logger.info("Creating a Habit!");

        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Habit name cannot be empty");
        }

        HabitEntity newHabit = new HabitEntity();
        newHabit.setName(request.name());
        newHabit.setPriority(request.priority());
        newHabit.setDescription(request.description());
        newHabit.setDate(request.date());

        if (repository.existsByName(newHabit.getName())) {
            throw new ConflictException("Habit already exists");
        }

        HabitEntity savedHabit = repository.save(newHabit);
        HabitResponse response = convertToResponse(savedHabit);

        addHateoasLinks(response);
        return response;
    }

    public HabitResponse update(Long id, HabitRequest request){

        logger.info("Updating a Habit!");

        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Habit name cannot be empty");
        }

        HabitEntity entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No records found"));
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPriority(request.priority());
        entity.setDate(request.date());

        HabitEntity updatedHabit = repository.save(entity);
        HabitResponse response = convertToResponse(updatedHabit);

        addHateoasLinks(response);
        return response;
    }

    public HabitResponse findById(Long id){
        logger.info("Finding a Habit!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No records found."));
        HabitResponse response = convertToResponse(entity);
        addHateoasLinks(response);
        return response;
    }

    public List<HabitResponse> findAll(){

        logger.info("Finding all Habits!");

        List<HabitEntity> entities = repository.findAll();
        List<HabitResponse> responses = entities.stream()
                        .map(this::convertToResponse)
                        .toList();
        responses.forEach(this::addHateoasLinks);
        return responses;
    }

    public void delete(Long id){

        logger.info("Deleting a Habit!");

        HabitEntity entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No records found."));

        repository.delete(entity);
    }

    private void addHateoasLinks(HabitResponse dto) {
        dto.add(linkTo(methodOn(HabitController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(HabitController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(HabitController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(HabitController.class).create(null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(HabitController.class).update(dto.getId(), null)).withRel("update").withType("PUT"));
    }

    //DTO conversion method
    private HabitResponse convertToResponse(HabitEntity entity){
        return new HabitResponse(
                entity.getId(),
                entity.getName(),
                entity.getPriority(),
                entity.getDescription(),
                entity.getDate()
        );
    }


}
