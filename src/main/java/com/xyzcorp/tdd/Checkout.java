package com.xyzcorp.tdd;

import java.time.LocalDate;

/**
 * Created by danno on 11/29/16.
 */
public class Checkout {
    private String name;
    private String title;
    private LocalDate checkoutDate;

    public Checkout(String name, String title, LocalDate checkoutDate) {
        this.name = name;
        this.title = title;
        this.checkoutDate = checkoutDate;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }
}
