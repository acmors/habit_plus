package HabitPlus.model.habit;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_habits")
public class HabitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "habit_name", nullable = false,length = 80)
    private String name;

    @Column(name = "habit_priority", nullable = false,length = 8)
    private String priority;

    @Column(name = "habit_description", nullable = false,length = 100)
    private String description;

    @Column(name = "habit_date", nullable = false)
    private Date date;

    public HabitEntity() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
