package HabitPlus.DTO.finance;

public record ExpenseRequest(
        String name,
        String description,
        String category,
        double value) {
}
