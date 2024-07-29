package com.shoppingplatform.price.domain.policy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendDiscountPolicy implements DiscountPolicy {

    @Override
    public BigDecimal applyDiscount(int quantity, BigDecimal price) {
        if (isWeekend()) {
            return price.multiply(BigDecimal.valueOf(0.95));
        }
        return price;
    }

    private boolean isWeekend() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
