package HabitPlus.unitests.mapper.mocks;

import HabitPlus.model.finance.ExpenseEntity;

import java.util.ArrayList;
import java.util.List;

public class MockExpense {


        public ExpenseEntity mockEntity() {
            return mockEntity(0);
        }

        public ExpenseDTO mockDTO() {
            return mockDTO(0);
        }

        public List<ExpenseEntity> mockEntityList() {
            List<ExpenseEntity> persons = new ArrayList<ExpenseEntity>();
            for (int i = 0; i < 14; i++) {
                persons.add(mockEntity(i));
            }
            return persons;
        }

        public List<ExpenseDTO> mockDTOList() {
            List<ExpenseDTO> persons = new ArrayList<>();
            for (int i = 0; i < 14; i++) {
                persons.add(mockDTO(i));
            }
            return persons;
        }

        public ExpenseEntity mockEntity(Integer number) {
            ExpenseEntity expenseEntity = new ExpenseEntity();
            expenseEntity.setId(number.longValue());
            expenseEntity.setExpenseName("Name Test" + number);
            expenseEntity.setExpenseDescription("Description Test" + number);
            expenseEntity.setExpenseCategory("Category Test" + number);
            expenseEntity.setExpenseValue(25D);
            return expenseEntity;
        }

        public ExpenseDTO mockDTO(Integer number) {
            ExpenseDTO expenseDTO = new ExpenseDTO();
            expenseDTO.setId(number.longValue());
            expenseDTO.setExpenseName("Name Test" + number);
            expenseDTO.setExpenseDescription("Description Test" + number);
            expenseDTO.setExpenseCategory("Category Test" + number);
            expenseDTO.setExpenseValue(25D);
            return expenseDTO;
        }

}

