package HabitPlus.repository.finance;

import HabitPlus.model.finance.ExpenseEntity;
import HabitPlus.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    boolean existsByNameAndUser(String getname, User user);
    List<ExpenseEntity> findByUser(User user);
    Optional<ExpenseEntity> findByIdAndUser(Long id, User user);
}
