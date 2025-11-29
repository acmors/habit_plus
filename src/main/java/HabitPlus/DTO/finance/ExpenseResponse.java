package HabitPlus.DTO.finance;

import org.springframework.hateoas.RepresentationModel;

public class ExpenseResponse extends RepresentationModel<ExpenseResponse> {
    private Long id;
    private String name;
    private String description;
    private String category;
    private double value;

    public ExpenseResponse(Long id, String name, String description, String category, double value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getdescription() {
        return description;
    }

    public String getcategory() {
        return category;
    }

    public double getvalue() {
        return value;
    }
}
