package HabitPlus.model.finance;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tb_name", nullable = false, length = 80)
    private String expenseName;

    @Column(name = "tb_description", nullable = false, length = 100)
    private String expenseDescription;

    @Column(name = "tb_category", nullable = false, length = 15)
    private String expenseCategory;

    @Column(name = "tb_value")
    private double expenseValue;

    public ExpenseEntity() {
    }

    public double getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(double expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
