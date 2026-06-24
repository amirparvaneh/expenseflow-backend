package com.expenseflow.category;

import com.expenseflow.budget.Budget;
import com.expenseflow.common.baseclass.BaseEntity;
import com.expenseflow.expense.domain.Expense;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Budget> budgets = new ArrayList<>();
}