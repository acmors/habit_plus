package HabitPlus.DTO.habit;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Date;

public class HabitDTO extends RepresentationModel<HabitDTO> {


    private Long id;
    private String name;
    private String priority;
    private String description;
    private LocalDate date;

    public HabitDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
