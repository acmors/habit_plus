package HabitPlus.service.finance;
import HabitPlus.DTO.finance.ExpenseDTO;
import HabitPlus.DTO.finance.ExpenseDTO;
import HabitPlus.model.finance.ExpenseEntity;
import HabitPlus.model.finance.IncomeEntity;
import HabitPlus.repository.finance.ExpenseRepository;
import HabitPlus.repository.finance.IncomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static HabitPlus.mapper.finance.IncomeMapper.parseListHabits;
import static HabitPlus.mapper.finance.IncomeMapper.parseObject;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    private Logger logger = LoggerFactory.getLogger(ExpenseService.class.getName());

    public ExpenseDTO create(ExpenseDTO expense){

        logger.info("Creating a Expense!");

        var entity = parseObject(expense, ExpenseEntity.class);
        return parseObject(repository.save(entity), ExpenseDTO.class);
    }

    public ExpenseDTO update(ExpenseDTO expense){

        logger.info("Updating a Expense!");

        ExpenseEntity entity = repository.findById(expense.getId())
                .orElseThrow(() -> new RuntimeException("No records found"));
        entity.setExpenseName(expense.getExpenseName());
        entity.setExpenseDescription(expense.getExpenseDescription());
        entity.setExpenseCategory(expense.getExpenseCategory());
        entity.setExpenseValue(expense.getExpenseValue());
        return parseObject(repository.save(entity), ExpenseDTO.class);
    }

    public ExpenseDTO findById(Long id){

        logger.info("Finding a Expense!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));
        return parseObject(entity, ExpenseDTO.class);
    }

    public List<ExpenseDTO> findAll(){

        logger.info("Finding all Expense!");

        List<ExpenseDTO> habits = new ArrayList<ExpenseDTO>();
        return parseListHabits(repository.findAll(), ExpenseDTO.class);
    }
    
    public void delete(Long id){

        logger.info("Deleting a Expense!");

        ExpenseEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));

        repository.delete(entity);
    }


}
