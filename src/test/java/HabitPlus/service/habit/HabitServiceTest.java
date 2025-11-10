package HabitPlus.service.habit;

import HabitPlus.DTO.habit.HabitDTO;
import HabitPlus.model.habit.HabitEntity;
import HabitPlus.repository.habit.HabitRepository;
import HabitPlus.unitests.mapper.mocks.MockHabit;
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
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class HabitServiceTest {

    MockHabit input;

    @InjectMocks
    private HabitService service;

    @Mock
    HabitRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockHabit();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        HabitEntity habit = input.mockEntity(1);
        HabitEntity persisted = habit;
        persisted.setId(1L);

        HabitDTO dto = input.mockDTO(1);
        when(repository.save(any(HabitEntity.class))).thenReturn(persisted);
        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getName());
        assertEquals("Priority Test1", result.getPriority());
        assertEquals("Description Test1", result.getDescription());
        assertNotNull(result.getDate());
    }

    @Test
    void update() {
        HabitEntity habit = input.mockEntity(1);
        HabitEntity persisted = habit;
        persisted.setId(1L);

        HabitDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        when(repository.save(habit)).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getName());
        assertEquals("Priority Test1", result.getPriority());
        assertEquals("Description Test1", result.getDescription());
        assertNotNull(result.getDate());

    }

    @Test
    void findById() {
        HabitEntity habit = input.mockEntity(1);
        HabitEntity persisted = habit;
        persisted.setId(1L);

        HabitDTO dto = input.mockDTO(1);
        when(repository.findById(1L)).thenReturn(Optional.of(habit));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", result.getName());
        assertEquals("Priority Test1", result.getPriority());
        assertEquals("Description Test1", result.getDescription());
        assertNotNull(result.getDate());
    }

    @Test
    void findAll() {
        List<HabitEntity> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<HabitDTO> habits = service.findAll();

        assertNotNull(habits);
        assertEquals(14, habits.size());

        var habitOne = habits.get(1);
        assertNotNull(habitOne);
        assertNotNull(habitOne.getId());
        assertNotNull(habitOne.getLinks());

        assertTrue(habitOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(habitOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(habitOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(habitOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(habitOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/habits/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test1", habitOne.getName());
        assertEquals("Priority Test1", habitOne.getPriority());
        assertEquals("Description Test1", habitOne.getDescription());
        assertNotNull(habitOne.getDate());

        var habitFour = habits.get(4);
        assertNotNull(habitFour);
        assertNotNull(habitFour.getId());
        assertNotNull(habitFour.getLinks());

        assertTrue(habitFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/habits/v1/4")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(habitFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(habitFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(habitFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(habitFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/habits/v1/4")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test4", habitFour.getName());
        assertEquals("Priority Test4", habitFour.getPriority());
        assertEquals("Description Test4", habitFour.getDescription());
        assertNotNull(habitFour.getDate());


        var habitSeven = habits.get(7);
        assertNotNull(habitSeven);
        assertNotNull(habitSeven.getId());
        assertNotNull(habitSeven.getLinks());

        assertTrue(habitSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/habits/v1/7")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(habitSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("GET")
                )
        );

        assertTrue(habitSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("POST")
                )
        );

        assertTrue(habitSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/habits/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertTrue(habitSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/habits/v1/7")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Name Test7", habitSeven.getName());
        assertEquals("Priority Test7", habitSeven.getPriority());
        assertEquals("Description Test7", habitSeven.getDescription());
        assertNotNull(habitFour.getDate());
    }

    @Test
    void delete() {
        HabitEntity habit = input.mockEntity(1);
        habit.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(habit));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(HabitEntity.class));
        verifyNoMoreInteractions(repository);
    }
}