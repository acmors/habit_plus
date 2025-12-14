package HabitPlus.service.habit;
import HabitPlus.DTO.habit.HabitRequest;
import HabitPlus.DTO.habit.HabitResponse;
import HabitPlus.config.AuthenticatedUser;
import HabitPlus.controllers.habit.HabitController;
import HabitPlus.exceptions.BadRequestException;
import HabitPlus.exceptions.ObjectNotFoundException;
import HabitPlus.model.habit.HabitEntity;
import HabitPlus.model.user.Role;
import HabitPlus.model.user.User;
import HabitPlus.repository.habit.HabitRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HabitService {

    @Autowired
    private HabitRepository repository;

    @Autowired
    private AuthenticatedUser authenticatedUser;


    private Logger logger = LoggerFactory.getLogger(HabitService.class.getName());


    public HabitResponse create(HabitRequest request){
        logger.info("Creating a Habit!");
        User user = authenticatedUser.get();

        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Habit name cannot be empty");
        }

        HabitEntity newHabit = new HabitEntity();
        newHabit.setName(request.name());
        newHabit.setPriority(request.priority());
        newHabit.setDescription(request.description());
        newHabit.setDate(request.date());
        newHabit.setUser(user);

        HabitEntity savedHabit = repository.save(newHabit);
        HabitResponse response = convertToResponse(savedHabit);

        addHateoasLinks(response);
        return response;
    }

    @Transactional
    public HabitResponse update(Long id, HabitRequest request){

        logger.info("Updating a Habit!");
        User user = authenticatedUser.get();

        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Habit name cannot be empty");
        }

        HabitEntity entity;
        if (user.getRole() == Role.ADMIN){
            entity = repository
                    .findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Habit not found."));
        }else{
            entity = repository
                    .findByIdAndUser(id, user)
                    .orElseThrow(() -> new ObjectNotFoundException("habit not found."));
        }

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
        User user = authenticatedUser.get();

        var entity = repository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new ObjectNotFoundException("Habit not found."));

        HabitResponse response = convertToResponse(entity);
        addHateoasLinks(response);
        return response;
    }

    public List<HabitResponse> findAll(){

        logger.info("Finding all Habits");
        User user = authenticatedUser.get();

        List<HabitEntity> entities;

        if (user.getRole() == Role.ADMIN) {
            logger.info("ADMIN role detected. Finding all existing Habits.");
            entities = repository.findAll();
        } else {
            logger.info("User role detected. Finding Habits for user: {}", user.getUsername());
            entities = repository.findByUser(user);
        }

        List<HabitResponse> responses = entities.stream()
                .map(this::convertToResponse)
                .toList();

        responses.forEach(this::addHateoasLinks);
        return responses;
    }

    @Transactional
    public void delete(Long id){

        logger.info("Deleting a Habit!");
        User user = authenticatedUser.get();

        HabitEntity entity;

        if (user.getRole() == Role.ADMIN){
            entity = repository
                    .findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Habit not found."));
        }else {
            entity = repository
                    .findByIdAndUser(id, user)
                    .orElseThrow(() -> new ObjectNotFoundException("Habit not found"));
        }

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
                entity.getDate(),
                entity.getUser()
        );
    }


}
