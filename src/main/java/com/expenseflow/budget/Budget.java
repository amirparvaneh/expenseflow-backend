package com.expenseflow.budget;


import com.expenseflow.category.Category;
import com.expenseflow.common.baseclass.BaseEntity;
import com.expenseflow.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Budget extends BaseEntity {

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
