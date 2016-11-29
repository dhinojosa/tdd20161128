package com.xyzcorp.tdd;


import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Random;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class LambdaDieTest {

    @Test
    @Category(value=UnitTest.class)
    public void testSimpleRoll4() {
        LambdaDie die = new LambdaDie(() -> 4);
        LambdaDie rolledDie = die.roll();
        assertThat(rolledDie.getPips()).isEqualTo(4);
    }


    @Test
    @Category(value=UnitTest.class)
    public void testIntegrationTest() {
        Random random = new Random();
        LambdaDie die = new LambdaDie(() -> random.nextInt(6) + 1);
        LambdaDie rolledDie = die.roll();
        assertThat(rolledDie.getPips()).isEqualTo(4);
    }
}
