package HabitPlus.service.finance;

import HabitPlus.DTO.finance.IncomeDTO;
import HabitPlus.model.finance.IncomeEntity;
import HabitPlus.repository.finance.ExpenseRepository;
import HabitPlus.repository.finance.IncomeRepository;
import HabitPlus.unitests.mapper.mocks.MockIncome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class IncomeServiceTest {

    MockIncome input;

    @InjectMocks
    private IncomeService service;

    @Mock
    IncomeRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockIncome();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        IncomeEntity income = input.mockEntity(1);
        IncomeEntity persisted = income;
        persisted.setId(1L);

        IncomeDTO dto = input.mockDTO(1);
        when(repository.save(any(IncomeEntity.class))).thenReturn(persisted);
        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getIncomeName());
        assertEquals("Description Test1", result.getIncomeDescription());
        assertEquals("Category Test1", result.getIncomeCategory());
        assertEquals(25D, result.getIncomeValue());
    }

    @Test
    void update() {
        IncomeEntity income = input.mockEntity(1);
        IncomeEntity persisted = income;
        persisted.setId(1L);

        IncomeDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(income));
        when(repository.save(income)).thenReturn(persisted);
        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getIncomeName());
        assertEquals("Description Test1", result.getIncomeDescription());
        assertEquals("Category Test1", result.getIncomeCategory());
        assertEquals(25D, result.getIncomeValue());

    }

    @Test
    void findById() {
        IncomeEntity income = input.mockEntity(1);
        IncomeEntity persisted = income;
        persisted.setId(1L);

        IncomeDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(income));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getIncomeName());
        assertEquals("Description Test1", result.getIncomeDescription());
        assertEquals("Category Test1", result.getIncomeCategory());
        assertEquals(25D, result.getIncomeValue());
    }

    @Test
    void findAll() {
        List<IncomeEntity> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<IncomeDTO> incomes = service.findAll();

        assertNotNull(incomes);
        assertEquals(14, incomes.size());

        var incomeOne = incomes.get(1);
        assertNotNull(incomeOne);
        assertNotNull(incomeOne.getId());
        assertNotNull(incomeOne.getLinks());

        assertTrue(incomeOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(incomeOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(incomeOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(incomeOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(incomeOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/income/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", incomeOne.getIncomeName());
        assertEquals("Description Test1", incomeOne.getIncomeDescription());
        assertEquals("Category Test1", incomeOne.getIncomeCategory());
        assertEquals(25D, incomeOne.getIncomeValue());

        var incomeFour = incomes.get(4);
        assertNotNull(incomeFour);
        assertNotNull(incomeFour.getId());
        assertNotNull(incomeFour.getLinks());

        assertTrue(incomeFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/income/v1/4")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(incomeFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(incomeFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(incomeFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(incomeFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/income/v1/4")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test4", incomeFour.getIncomeName());
        assertEquals("Description Test4", incomeFour.getIncomeDescription());
        assertEquals("Category Test4", incomeFour.getIncomeCategory());
        assertEquals(25D, incomeFour.getIncomeValue());


        var incomeSeven = incomes.get(7);
        assertNotNull(incomeSeven);
        assertNotNull(incomeSeven.getId());
        assertNotNull(incomeSeven.getLinks());

        assertTrue(incomeSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/income/v1/7")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(incomeSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(incomeSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(incomeSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/income/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(incomeSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/income/v1/7")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test7", incomeSeven.getIncomeName());
        assertEquals("Description Test7", incomeSeven.getIncomeDescription());
        assertEquals("Category Test7", incomeSeven.getIncomeCategory());
        assertEquals(25D, incomeSeven.getIncomeValue());

    }

    @Test
    void delete() {
        IncomeEntity income = input.mockEntity(1);
        income.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(income));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(IncomeEntity.class));
        verifyNoMoreInteractions(repository);
    }

}