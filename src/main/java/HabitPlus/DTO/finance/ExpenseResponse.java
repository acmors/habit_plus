package HabitPlus.DTO.finance;

import HabitPlus.model.finance.ExpenseCategory;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

public class ExpenseResponse extends RepresentationModel<ExpenseResponse> {
    private Long id;
    private String name;
    private String description;
    private ExpenseCategory category;
    private BigDecimal value;

    public ExpenseResponse(Long id, String name, String description, ExpenseCategory category, BigDecimal value) {
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

    public ExpenseCategory getcategory() {
        return category;
    }

    public BigDecimal getvalue() {
        return value;
    }
}
