package HabitPlus.DTO.habit;

import HabitPlus.model.habit.Priority;
import HabitPlus.model.user.User;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class HabitResponse extends  RepresentationModel<HabitResponse>{

    Long id;
    String name;
    Priority priority;
    String description;
    LocalDate date;
    User user;

    public HabitResponse(Long id, String name, Priority priority, String description, LocalDate date, User user) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.description = description;
        this.date = date;
        this.user = user;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
