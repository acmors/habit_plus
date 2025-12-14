package HabitPlus.DTO.habit;
import HabitPlus.model.habit.Priority;
import HabitPlus.model.user.User;

import java.time.LocalDate;

public record HabitRequest(String name,
                           Priority priority,
                           String description,
                           LocalDate date,
                           User user) {
}
