package HabitPlus.service.finance;
import HabitPlus.DTO.finance.IncomeRequest;
import HabitPlus.DTO.finance.IncomeResponse;
import HabitPlus.config.AuthenticatedUser;
import HabitPlus.controllers.finance.IncomeController;
import HabitPlus.exceptions.BadRequestException;
import HabitPlus.exceptions.ConflictException;
import HabitPlus.exceptions.ObjectNotFoundException;
import HabitPlus.model.finance.IncomeEntity;
import HabitPlus.model.user.Role;
import HabitPlus.model.user.User;
import HabitPlus.repository.finance.IncomeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    private Logger logger = LoggerFactory.getLogger(IncomeService.class.getName());

    public IncomeResponse create(IncomeRequest request){

        logger.info("Creating a Income!");
        User user = authenticatedUser.get();

        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Income name cannot be empty");
        }

        IncomeEntity newIncome = new IncomeEntity();
        newIncome.setName(request.name());
        newIncome.setDescription(request.description());
        newIncome.setCategory(request.category());
        newIncome.setValue(request.value());
        newIncome.setUser(user);

        if (repository.existsByNameAndUser(newIncome.getName(), user)) throw new ConflictException("Income already exists");

        IncomeEntity savedIncome = repository.save(newIncome);
        IncomeResponse response = convertToResponse(savedIncome);

        addHateoasLinks(response);
        return response;
    }

    @Transactional
    public IncomeResponse update(Long id, IncomeRequest request){
        logger.info("Updating a Income!");
        User user = authenticatedUser.get();

        if (request.name() == null || request.name().isBlank()) {
            throw new BadRequestException("Income name cannot be empty");
        }

        IncomeEntity entity;
        if (user.getRole() == Role.ADMIN){
            entity = repository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Income not found."));
        }else {
            entity = repository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ObjectNotFoundException("Income not found."));
        }

        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setValue(request.value());

        IncomeEntity updatedIncome = repository.save(entity);
        IncomeResponse response = convertToResponse(updatedIncome);

        addHateoasLinks(response);
        return response;
    }

    public IncomeResponse findById(Long id){

        logger.info("Finding a Income!");
        User user = authenticatedUser.get();

        var entity = repository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new ObjectNotFoundException("Income not found"));

        IncomeResponse response = convertToResponse(entity);
        addHateoasLinks(response);
        return response;
    }

    public List<IncomeResponse> findAll(){

        logger.info("Finding all Incomes!");
        User user = authenticatedUser.get();

        List<IncomeEntity> incomes;

        if(user.getRole() == Role.ADMIN){
            logger.info("Admin role detected. Finding all existing Incomes.");
            incomes = repository.findAll();
        }else {
            logger.info("User role detected. Finding Incomes for user: {}", user.getUsername());
            incomes = repository.findByUser(user);
        }

        List<IncomeResponse> responses = incomes.stream()
                        .map(this::convertToResponse)
                        .toList();
        responses.forEach(this::addHateoasLinks);
        return responses;
    }

    @Transactional
    public void delete(Long id){

        logger.info("Deleting a Income!");
        User user = authenticatedUser.get();

        IncomeEntity entity;
        if (user.getRole() == Role.ADMIN){
            entity = repository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Expense not found."));
        }else {
            entity = repository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ObjectNotFoundException("Expense not found."));
        }

        repository.delete(entity);
    }

    private void addHateoasLinks(IncomeResponse dto) {
        dto.add(linkTo(methodOn(IncomeController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(IncomeController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(IncomeController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(IncomeController.class).create(null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(IncomeController.class).update(dto.getId(), null)).withRel("update").withType("PUT"));
    }

    //DTO conversion method
    private IncomeResponse convertToResponse(IncomeEntity entity){
        return new IncomeResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getValue()
        );
    }
}
