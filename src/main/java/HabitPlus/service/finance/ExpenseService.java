package HabitPlus.service.finance;
import HabitPlus.DTO.finance.ExpenseDTO;
import HabitPlus.controllers.finance.ExpenseController;
import HabitPlus.model.finance.ExpenseEntity;
import HabitPlus.repository.finance.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static HabitPlus.mapper.finance.IncomeMapper.parseListHabits;
import static HabitPlus.mapper.finance.IncomeMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    private Logger logger = LoggerFactory.getLogger(ExpenseService.class.getName());

    public ExpenseDTO create(ExpenseDTO expense){

        logger.info("Creating a Expense!");

        var entity = parseObject(expense, ExpenseEntity.class);
        var dto = parseObject(repository.save(entity), ExpenseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public ExpenseDTO update(ExpenseDTO expense){

        logger.info("Updating a Expense!");

        ExpenseEntity entity = repository.findById(expense.getId())
                .orElseThrow(() -> new RuntimeException("No records found"));
        entity.setExpenseName(expense.getExpenseName());
        entity.setExpenseDescription(expense.getExpenseDescription());
        entity.setExpenseCategory(expense.getExpenseCategory());
        entity.setExpenseValue(expense.getExpenseValue());
        var dto = parseObject(repository.save(entity), ExpenseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public ExpenseDTO findById(Long id){

        logger.info("Finding a Expense!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));
        var dto = parseObject(entity, ExpenseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public List<ExpenseDTO> findAll(){

        logger.info("Finding all Expense!");

        List<ExpenseDTO> habits = new ArrayList<ExpenseDTO>();
        var dto = parseListHabits(repository.findAll(), ExpenseDTO.class);
        dto.forEach(this::addHateoasLinks);
        return dto;
    }
    
    public void delete(Long id){

        logger.info("Deleting a Expense!");

        ExpenseEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));

        repository.delete(entity);

    }

    private void addHateoasLinks(ExpenseDTO dto) {
        dto.add(linkTo(methodOn(ExpenseController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ExpenseController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(ExpenseController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(ExpenseController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(ExpenseController.class).update(dto)).withRel("update").withType("PUT"));
    }


}
