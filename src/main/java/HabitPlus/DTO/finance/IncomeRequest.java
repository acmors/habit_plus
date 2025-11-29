package HabitPlus.DTO.finance;

public record IncomeRequest(
        String name,
        String description,
        String category,
        double value) {
}
