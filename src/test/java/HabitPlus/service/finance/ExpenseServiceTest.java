package HabitPlus.service.finance;

import HabitPlus.model.finance.ExpenseEntity;
import HabitPlus.repository.finance.ExpenseRepository;
import HabitPlus.unitests.mapper.mocks.MockExpense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    MockExpense input;

    @InjectMocks
    private ExpenseService service;

    @Mock
    ExpenseRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockExpense();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        ExpenseEntity expense = input.mockEntity(1);
        ExpenseEntity persisted = expense;
        persisted.setId(1L);

        ExpenseDTO dto = input.mockDTO(1);
        when(repository.save(any(ExpenseEntity.class))).thenReturn(persisted);
        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getExpenseName());
        assertEquals("Description Test1", result.getExpenseDescription());
        assertEquals("Category Test1", result.getExpenseCategory());
        assertEquals(25D, result.getExpenseValue());
    }

    @Test
    void update() {
        ExpenseEntity expense = input.mockEntity(1);
        ExpenseEntity persisted = expense;
        persisted.setId(1L);

        ExpenseDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(expense));
        when(repository.save(expense)).thenReturn(persisted);
        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getExpenseName());
        assertEquals("Description Test1", result.getExpenseDescription());
        assertEquals("Category Test1", result.getExpenseCategory());
        assertEquals(25D, result.getExpenseValue());

    }

    @Test
    void findById() {
        ExpenseEntity expense = input.mockEntity(1);
        ExpenseEntity persisted = expense;
        persisted.setId(1L);

        ExpenseDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(expense));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getExpenseName());
        assertEquals("Description Test1", result.getExpenseDescription());
        assertEquals("Category Test1", result.getExpenseCategory());
        assertEquals(25D, result.getExpenseValue());
    }

    @Test
    void findAll() {
        List<ExpenseEntity> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<ExpenseDTO> expenses = service.findAll();

        assertNotNull(expenses);
        assertEquals(14, expenses.size());

        var expenseOne = expenses.get(1);
        assertNotNull(expenseOne);
        assertNotNull(expenseOne.getId());
        assertNotNull(expenseOne.getLinks());

        assertTrue(expenseOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(expenseOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(expenseOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(expenseOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(expenseOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/expense/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", expenseOne.getExpenseName());
        assertEquals("Description Test1", expenseOne.getExpenseDescription());
        assertEquals("Category Test1", expenseOne.getExpenseCategory());
        assertEquals(25D, expenseOne.getExpenseValue());

        var expenseFour = expenses.get(4);
        assertNotNull(expenseFour);
        assertNotNull(expenseFour.getId());
        assertNotNull(expenseFour.getLinks());

        assertTrue(expenseFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/expense/v1/4")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(expenseFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(expenseFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(expenseFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(expenseFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/expense/v1/4")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test4", expenseFour.getExpenseName());
        assertEquals("Description Test4", expenseFour.getExpenseDescription());
        assertEquals("Category Test4", expenseFour.getExpenseCategory());
        assertEquals(25D, expenseFour.getExpenseValue());


        var expenseSeven = expenses.get(7);
        assertNotNull(expenseSeven);
        assertNotNull(expenseSeven.getId());
        assertNotNull(expenseSeven.getLinks());

        assertTrue(expenseSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/expense/v1/7")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(expenseSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(expenseSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(expenseSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/expense/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(expenseSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/expense/v1/7")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test7", expenseSeven.getExpenseName());
        assertEquals("Description Test7", expenseSeven.getExpenseDescription());
        assertEquals("Category Test7", expenseSeven.getExpenseCategory());
        assertEquals(25D, expenseSeven.getExpenseValue());

    }

    @Test
    void delete() {
        ExpenseEntity expense = input.mockEntity(1);
        expense.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(expense));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(ExpenseEntity.class));
        verifyNoMoreInteractions(repository);
    }
}