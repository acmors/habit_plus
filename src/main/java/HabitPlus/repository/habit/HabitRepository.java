package HabitPlus.repository.habit;
import HabitPlus.model.habit.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<HabitEntity, Long> {
    boolean existsByName(String name);
}
