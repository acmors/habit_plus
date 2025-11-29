package HabitPlus.repository.finance;

import HabitPlus.model.finance.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    boolean existsByName(String getname);
}
