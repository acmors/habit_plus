package HabitPlus.DTO.habit;
import java.time.LocalDate;

public record HabitRequest(String name,
                           String priority,
                           String description,
                           LocalDate date) {
}
