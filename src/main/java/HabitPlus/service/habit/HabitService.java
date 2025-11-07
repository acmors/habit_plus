package HabitPlus.service.habit;
import HabitPlus.DTO.habit.HabitDTO;
import static HabitPlus.mapper.habit.HabitMapper.parseObject;
import static HabitPlus.mapper.habit.HabitMapper.parseListHabits;
import HabitPlus.model.habit.HabitEntity;
import HabitPlus.repository.habit.HabitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        var entity = parseObject(habit, HabitEntity.class);
        return parseObject(repository.save(entity), HabitDTO.class);
    }

    public HabitDTO update(HabitDTO habit){

        logger.info("Updating a Habit!");

        HabitEntity entity = repository.findById(habit.getId())
                .orElseThrow(() -> new RuntimeException("No records found"));
        entity.setName(habit.getName());
        entity.setDescription(habit.getDescription());
        entity.setPriority(habit.getPriority());
        entity.setDate(habit.getDate());
        return parseObject(repository.save(entity), HabitDTO.class);
    }

    public HabitDTO findById(Long id){

        logger.info("Finding a Habit!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));
        return parseObject(entity, HabitDTO.class);
    }

    public List<HabitDTO> findAll(){

        logger.info("Finding all Habits!");

        List<HabitDTO> habits = new ArrayList<HabitDTO>();
        return parseListHabits(repository.findAll(), HabitDTO.class);
    }

    public void delete(Long id){

        logger.info("Deleting a Habit!");

        HabitEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No records found."));

        repository.delete(entity);
    }


}
