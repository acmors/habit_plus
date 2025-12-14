package HabitPlus.repository.finance;

import HabitPlus.model.finance.IncomeEntity;
import HabitPlus.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    boolean existsByNameAndUser(String name, User user);
    Optional<IncomeEntity> findByIdAndUser(Long id, User user);
    List<IncomeEntity> findByUser(User user);
}
