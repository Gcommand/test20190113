package com.test.res.datasource.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by user on 11/1/2019.
 */
@Entity
@Table(name = "test", schema = "", catalog = "res")
public class TestEntity {
    private int userId;
    private BigDecimal amount;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEntity that = (TestEntity) o;

        if (userId != that.userId) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
