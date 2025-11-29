package HabitPlus.DTO.habit;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class HabitResponse extends RepresentationModel<HabitResponse> {

    Long id;
    String name;
    String priority;
    String description;
    LocalDate date;

    public HabitResponse(Long id, String name, String priority, String description, LocalDate date) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}
