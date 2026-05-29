package com.expenseflow.expense.domain;


import com.expenseflow.common.baseclass.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Expense extends BaseEntity {
    @Column
    private String description;
}
