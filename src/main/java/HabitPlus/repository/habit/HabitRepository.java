package HabitPlus.repository.habit;
import HabitPlus.model.habit.HabitEntity;
import HabitPlus.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<HabitEntity, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndUser(String name, User currentUser);

    List<HabitEntity> findByUser(User user);

    Optional<HabitEntity> findByIdAndUser(Long id, User user);
}
