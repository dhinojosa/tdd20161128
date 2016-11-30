package com.xyzcorp.tdd;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    @Test
    public void testPenaltyOnSameDate() {
        LocalDate checkoutDate = LocalDate.of(2015, 1, 10);
        LocalDate todayDate = LocalDate.of(2015, 1, 10);

        Calculator calculator = new Calculator();
        calculator.setRate(10);
        assertThat(calculator.calculate(checkoutDate, todayDate)).isEqualTo(0);
    }

    @Test
    public void testPenaltyOneMonthOneDayApartOneDay() {
        LocalDate checkoutDate = LocalDate.of(2015, 1, 10);
        LocalDate todayDate = LocalDate.of(2015, 2, 11);

        Calculator calculator = new Calculator();
        calculator.setRate(10);
        assertThat(calculator.calculate(checkoutDate, todayDate)).isEqualTo(10);
    }


    @Test
    public void testPenaltyOneMonthOneDayApartExactly() {
        LocalDate checkoutDate = LocalDate.of(2015, 1, 10);
        LocalDate todayDate = LocalDate.of(2015, 2, 10);

        Calculator calculator = new Calculator();
        calculator.setRate(10);
        assertThat(calculator.calculate(checkoutDate, todayDate)).isEqualTo(0);
    }

    //NoRed
    @Test
    public void testLeapYear() {
        LocalDate checkoutDate = LocalDate.of(2016, 1, 31);
        LocalDate todayDate = LocalDate.of(2016, 2, 28);

        Calculator calculator = new Calculator();
        calculator.setRate(10);
        assertThat(calculator.calculate(checkoutDate, todayDate)).isEqualTo(0);
    }

    //NoRed
    @Test
    public void testLeapYearWithFeb29() {
        LocalDate checkoutDate = LocalDate.of(2016, 1, 31);
        LocalDate todayDate = LocalDate.of(2016, 2, 29);

        Calculator calculator = new Calculator();
        calculator.setRate(10);
        assertThat(calculator.calculate(checkoutDate, todayDate)).isEqualTo(0);
    }

    //NoRed
    @Test
    public void testLeapYearWithMar1() {
        LocalDate checkoutDate = LocalDate.of(2016, 1, 31);
        LocalDate todayDate = LocalDate.of(2016, 3, 1);

        Calculator calculator = new Calculator();
        calculator.setRate(10);
        assertThat(calculator.calculate(checkoutDate, todayDate)).isEqualTo(10);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCheckoutDateIsAfterTodaysDate() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Checkout date is greater than today\'s date");

        LocalDate checkoutDate = LocalDate.of(2016, 1, 31);
        LocalDate todayDate = LocalDate.of(2015, 2, 28);

        Calculator calculator = new Calculator();
        calculator.setRate(10);
        calculator.calculate(checkoutDate, todayDate);
    }

    //TODO: test nulls for checkoutdate, todaysdate, neg. penalty, 0 penalty
}

