package HabitPlus.repository.finance;

import HabitPlus.model.finance.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    boolean existsByName(String name);
}
