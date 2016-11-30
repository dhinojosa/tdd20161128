package com.xyzcorp.tdd;

import java.util.Objects;
import java.util.Random;

public class DieImpl implements Die {
    private static final int DEFAULT_PIPS = 1;
    protected static final String RANDOM_IS_NULL = "Random is null";

    private final int pips;  //should be final
    private final Random random;

    protected DieImpl(Random random) {
       this(random, DEFAULT_PIPS);
    }

    private DieImpl(Random random, int i) {
        Objects.requireNonNull(random, RANDOM_IS_NULL);
        this.random = random;
        this.pips = i;
    }

    @Override
    public int getPips() {
        return pips;
    }

    @Override
    public Die roll() {
        return new DieImpl(random, random.nextInt(6) + 1);
    }

    //Factory
    public static Die create() {
        return new DieImpl(new Random());
    }
}
