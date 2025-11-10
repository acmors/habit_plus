package HabitPlus.unitests.mapper.mocks;
import HabitPlus.DTO.habit.HabitDTO;
import HabitPlus.model.habit.HabitEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockHabit{


        public HabitEntity mockEntity() {
            return mockEntity(0);
        }

        public HabitDTO mockDTO() {
            return mockDTO(0);
        }

        public List<HabitEntity> mockEntityList() {
            List<HabitEntity> persons = new ArrayList<HabitEntity>();
            for (int i = 0; i < 14; i++) {
                persons.add(mockEntity(i));
            }
            return persons;
        }

        public List<HabitDTO> mockDTOList() {
            List<HabitDTO> persons = new ArrayList<>();
            for (int i = 0; i < 14; i++) {
                persons.add(mockDTO(i));
            }
            return persons;
        }

        public HabitEntity mockEntity(Integer number) {
            HabitEntity habitEntity = new HabitEntity();
            habitEntity.setId(number.longValue());
            habitEntity.setName("Name Test" + number);
            habitEntity.setPriority("Priority Test" + number);
            habitEntity.setDescription("Description Test" + number);
            habitEntity.setDate(new Date());
            return habitEntity;
        }

        public HabitDTO mockDTO(Integer number) {
            HabitDTO habitEntity = new HabitDTO();
            habitEntity.setId(number.longValue());
            habitEntity.setName("Name Test" + number);
            habitEntity.setPriority("Priority Test" + number);
            habitEntity.setDescription("Description Test" + number);
            habitEntity.setDate(new Date());
            return habitEntity;
        }

}

