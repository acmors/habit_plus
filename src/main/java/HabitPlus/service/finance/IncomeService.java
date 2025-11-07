package HabitPlus.service.finance;
import HabitPlus.DTO.finance.IncomeDTO;
import HabitPlus.model.finance.IncomeEntity;
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
public class IncomeService {

    @Autowired
    private IncomeRepository repository;

    private Logger logger = LoggerFactory.getLogger(IncomeService.class.getName());

    public IncomeDTO create(IncomeDTO income){

        logger.info("Creating a Income!");

        var entity = parseObject(income, IncomeEntity.class);
        return parseObject(repository.save(entity), IncomeDTO.class);
    }

    public IncomeDTO update(IncomeDTO income){

        logger.info("Updating a Income!");

        IncomeEntity entity = repository.findById(income.getId())
                .orElseThrow(() -> new RuntimeException("No records found"));
        entity.setIncomeName(income.getIncomeName());
        entity.setIncomeDescription(income.getIncomeDescription());
        entity.setIncomeCategory(income.getIncomeCategory());
        entity.setIncomeValue(income.getIncomeValue());
        return parseObject(repository.save(entity), IncomeDTO.class);
    }

    public IncomeDTO findById(Long id){

        logger.info("Finding a Income!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));
        return parseObject(entity, IncomeDTO.class);
    }

    public List<IncomeDTO> findAll(){

        logger.info("Finding all Incomes!");

        List<IncomeDTO> habits = new ArrayList<IncomeDTO>();
        return parseListHabits(repository.findAll(), IncomeDTO.class);
    }
    
    public void delete(Long id){

        logger.info("Deleting a Income!");

        IncomeEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));

        repository.delete(entity);
    }


}
