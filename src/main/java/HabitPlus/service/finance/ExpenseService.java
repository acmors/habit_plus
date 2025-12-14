package HabitPlus.service.finance;
import HabitPlus.DTO.finance.ExpenseRequest;
import HabitPlus.DTO.finance.ExpenseResponse;
import HabitPlus.config.AuthenticatedUser;
import HabitPlus.controllers.finance.ExpenseController;
import HabitPlus.exceptions.BadRequestException;
import HabitPlus.exceptions.ConflictException;
import HabitPlus.exceptions.ObjectNotFoundException;
import HabitPlus.model.finance.ExpenseEntity;
import HabitPlus.model.user.Role;
import HabitPlus.model.user.User;
import HabitPlus.repository.finance.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    private Logger logger = LoggerFactory.getLogger(ExpenseService.class.getName());

    public ExpenseResponse create(ExpenseRequest request){
        logger.info("Creating a Expense!");
        User user = authenticatedUser.get();

        if (request.name() == null || request.name().isBlank()) throw new BadRequestException("Expense name cannot be empty");

        ExpenseEntity newExpense = new ExpenseEntity();
        newExpense.setName(request.name());
        newExpense.setDescription(request.description());
        newExpense.setCategory(request.category());
        newExpense.setValue(request.value());
        newExpense.setUser(user);
        if (repository.existsByNameAndUser(newExpense.getName(), user)) throw new ConflictException("Expense Already exists.");

        ExpenseEntity savedExpense = repository.save(newExpense);
        ExpenseResponse response = convertToResponse(savedExpense);

        addHateoasLinks(response);
        return response;
    }

    @Transactional
    public ExpenseResponse update(Long id, ExpenseRequest request){
        logger.info("Updating a Expense!");
        User user = authenticatedUser.get();

        if (request.name() == null || request.name().isBlank()) throw new BadRequestException("Habit name cannot be empty");

        ExpenseEntity entity;
        if (user.getRole() == Role.ADMIN){
            entity = repository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Expense not found."));
        }else {
            entity = repository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ObjectNotFoundException("Expense not found."));
        }

        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setValue(request.value());

        ExpenseEntity updatedExpense = repository.save(entity);
        ExpenseResponse response = convertToResponse(updatedExpense);

        addHateoasLinks(response);
        return response;
    }

    public ExpenseResponse findById(Long id){

        logger.info("Finding a Expense!");
        User user = authenticatedUser.get();

        var entity = repository
                .findByIdAndUser(id, user)
                .orElseThrow(() -> new ObjectNotFoundException("Expense not found"));

        ExpenseResponse response = convertToResponse(entity);
        addHateoasLinks(response);
        return response;
    }

    public List<ExpenseResponse> findAll(){

        logger.info("Finding all Expense!");
        User user = authenticatedUser.get();

        List<ExpenseEntity> expenses;

        if (user.getRole() == Role.ADMIN) {
            logger.info("Admin role detected. Finding all existing Expenses.");
            expenses = repository.findAll();
        }else {
            logger.info("User role detected. Finding Expenses for user: {}", user.getUsername());
            expenses = repository.findByUser(user);
        }

        List<ExpenseResponse> responses = expenses.stream()
                .map(this::convertToResponse)
                .toList();

        responses.forEach(this::addHateoasLinks);
        return responses;
    }

    @Transactional
    public void delete(Long id){

        logger.info("Deleting an Expense!");
        User user = authenticatedUser.get();

        ExpenseEntity entity;

        if (user.getRole() == Role.ADMIN){
            entity = repository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Expense not found."));
        }else{
            entity = repository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ObjectNotFoundException("Expense not found."));
        }

        repository.delete(entity);

    }

    private void addHateoasLinks(ExpenseResponse dto) {
        dto.add(linkTo(methodOn(ExpenseController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ExpenseController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(ExpenseController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(ExpenseController.class).create(null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(ExpenseController.class).update(dto.getId(), null)).withRel("update").withType("PUT"));
    }

    //DTO conversation method
    private ExpenseResponse convertToResponse(ExpenseEntity entity){
        return new ExpenseResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getValue()
        );
    }


}
