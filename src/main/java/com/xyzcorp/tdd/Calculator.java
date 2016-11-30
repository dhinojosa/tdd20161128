package com.xyzcorp.tdd;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.MONTHS;

/**
 * Created by danno on 11/29/16.
 */
public class Calculator {

    private int rate;

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int calculate(LocalDate checkoutDate, LocalDate todayDate) {
        if (checkoutDate.isAfter(todayDate)) throw new
                IllegalArgumentException("Checkout date is greater than today\'s date");
        return ((int) MONTHS.between(checkoutDate.plusDays(1), todayDate)) * rate;
    }
}
