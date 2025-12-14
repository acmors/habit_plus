package HabitPlus.DTO.finance;

import HabitPlus.model.finance.IncomeCategory;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

public class IncomeResponse extends RepresentationModel<IncomeResponse> {
    private Long id;
    private String name;
    private String description;
    private IncomeCategory category;
    private BigDecimal value;

    public IncomeResponse(Long id, String name, String description, IncomeCategory category, BigDecimal value) {
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

    public IncomeCategory getcategory() {
        return category;
    }

    public BigDecimal getvalue() {
        return value;
    }
}
