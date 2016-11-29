package com.xyzcorp.tdd;

import java.util.Objects;
import java.util.Random;

public class Die {
    private static final int DEFAULT_PIPS = 1;
    protected static final String RANDOM_IS_NULL = "Random is null";

    private final int pips;  //should be final
    private final Random random;

    public Die(Random random) {
       this(random, DEFAULT_PIPS);
    }

    private Die(Random random, int i) {
        Objects.requireNonNull(random, RANDOM_IS_NULL);
        this.random = random;
        this.pips = i;
    }

    public int getPips() {
        return pips;
    }

    public Die roll() {
        Die die = new Die(random, random.nextInt(7));
        random.nextLong();
        return die;
    }
}
