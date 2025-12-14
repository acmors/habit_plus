package HabitPlus.DTO.finance;

import HabitPlus.model.finance.IncomeCategory;

import java.math.BigDecimal;

public record IncomeRequest(
        String name,
        String description,
        IncomeCategory category,
        BigDecimal value) {
}
