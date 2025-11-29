package HabitPlus.service.finance;
import HabitPlus.DTO.finance.ExpenseRequest;
import HabitPlus.DTO.finance.ExpenseResponse;
import HabitPlus.controllers.finance.ExpenseController;
import HabitPlus.exceptions.BadRequestException;
import HabitPlus.exceptions.ConflictException;
import HabitPlus.exceptions.ObjectNotFoundException;
import HabitPlus.model.finance.ExpenseEntity;
import HabitPlus.repository.finance.ExpenseRepository;
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

    private Logger logger = LoggerFactory.getLogger(ExpenseService.class.getName());

    public ExpenseResponse create(ExpenseRequest request){
        logger.info("Creating a Expense!");
        if (request.name() == null || request.name().isBlank()) throw new BadRequestException("Habit name cannot be empty");

        ExpenseEntity newExpense = new ExpenseEntity();
        newExpense.setname(request.name());
        newExpense.setdescription(request.description());
        newExpense.setcategory(request.category());
        newExpense.setvalue(request.value());
        if (repository.existsByName(newExpense.getname())) throw new ConflictException("Expense Already exists.");

        ExpenseEntity savedExpense = repository.save(newExpense);
        ExpenseResponse response = convertToResponse(savedExpense);

        addHateoasLinks(response);
        return response;
    }

    public ExpenseResponse update(Long id, ExpenseRequest request){
        logger.info("Updating a Expense!");
        if (request.name() == null || request.name().isBlank()) throw new BadRequestException("Habit name cannot be empty");

        ExpenseEntity entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Expense not found"));
        entity.setname(request.name());
        entity.setdescription(request.description());
        entity.setcategory(request.category());
        entity.setvalue(request.value());

        ExpenseEntity updatedExpense = repository.save(entity);
        ExpenseResponse response = convertToResponse(updatedExpense);

        addHateoasLinks(response);
        return response;
    }

    public ExpenseResponse findById(Long id){

        logger.info("Finding a Expense!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Expense not found"));
        ExpenseResponse response = convertToResponse(entity);

        addHateoasLinks(response);
        return response;
    }

    public List<ExpenseResponse> findAll(){

        logger.info("Finding all Expense!");

        List<ExpenseEntity> expenses = repository.findAll();
        List<ExpenseResponse> responses = expenses.stream()
                .map(this::convertToResponse)
                .toList();

        responses.forEach(this::addHateoasLinks);
        return responses;
    }
    
    public void delete(Long id){

        logger.info("Deleting a Expense!");

        ExpenseEntity entity = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Expense not found"));

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
                entity.getname(),
                entity.getcategory(),
                entity.getdescription(),
                entity.getvalue()
        );
    }


}
