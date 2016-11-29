package com.xyzcorp.tdd;

import java.util.function.Supplier;

/**
 * Created by danno on 11/29/16.
 */
public class LambdaDie implements Die{
    public LambdaDie(Supplier<Integer> supplier) {
    }

    public LambdaDie roll() {
        return null;
    }

    public int getPips() {
        return 0;
    }
}
