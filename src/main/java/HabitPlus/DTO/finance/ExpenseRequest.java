package HabitPlus.DTO.finance;

import HabitPlus.model.finance.ExpenseCategory;

import java.math.BigDecimal;

public record ExpenseRequest(
        String name,
        String description,
        ExpenseCategory category,
        BigDecimal value) {
}
