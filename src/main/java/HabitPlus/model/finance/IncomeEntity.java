package HabitPlus.model.finance;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_income")
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tb_name", nullable = false, length = 80)
    private String incomeName;

    @Column(name = "tb_description", nullable = false, length = 100)
    private String incomeDescription;

    @Column(name = "tb_category", nullable = false, length = 15)
    private String incomeCategory;

    @Column(name = "tb_value")
    private double incomeValue;

    public IncomeEntity() {
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }

    public String getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(String incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    public double getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(double incomeValue) {
        this.incomeValue = incomeValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
