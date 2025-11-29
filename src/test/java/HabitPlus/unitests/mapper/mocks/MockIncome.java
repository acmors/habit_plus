package HabitPlus.unitests.mapper.mocks;
import HabitPlus.model.finance.IncomeEntity;
import java.util.ArrayList;
import java.util.List;

public class MockIncome {


        public IncomeEntity mockEntity() {
            return mockEntity(0);
        }

        public IncomeDTO mockDTO() {
            return mockDTO(0);
        }

        public List<IncomeEntity> mockEntityList() {
            List<IncomeEntity> persons = new ArrayList<IncomeEntity>();
            for (int i = 0; i < 14; i++) {
                persons.add(mockEntity(i));
            }
            return persons;
        }

        public List<IncomeDTO> mockDTOList() {
            List<IncomeDTO> persons = new ArrayList<>();
            for (int i = 0; i < 14; i++) {
                persons.add(mockDTO(i));
            }
            return persons;
        }

        public IncomeEntity mockEntity(Integer number) {
            IncomeEntity incomeEntity = new IncomeEntity();
            incomeEntity.setId(number.longValue());
            incomeEntity.setIncomeName("Name Test" + number);
            incomeEntity.setIncomeDescription("Description Test" + number);
            incomeEntity.setIncomeCategory("Category Test" + number);
            incomeEntity.setIncomeValue(25D);
            return incomeEntity;
        }

        public IncomeDTO mockDTO(Integer number) {
            IncomeDTO incomeDTO = new IncomeDTO();
            incomeDTO.setId(number.longValue());
            incomeDTO.setIncomeName("Name Test" + number);
            incomeDTO.setIncomeDescription("Description Test" + number);
            incomeDTO.setIncomeCategory("Category Test" + number);
            incomeDTO.setIncomeValue(25D);
            return incomeDTO;
        }

}

