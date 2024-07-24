package com.shoppingplatform.price.domain.policy;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendDiscountPolicy implements DiscountPolicy {

    @Override
    public double applyDiscount(int quantity, double price) {
        if (isWeekend()) {
            return price * 0.95;
        }
        return price;
    }

    private boolean isWeekend() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
