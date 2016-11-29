package com.xyzcorp.tdd;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;

public class DieTest {

    //Design: Keep the pip value, and after roll, new instance.

    @Test
    @Category(value=UnitTest.class)
    public void testDefaultIs1() throws Exception {
        Die die = new Die(createMock(Random.class)); //Reliant Randomizer
        assertThat(die.getPips()).isEqualTo(1);
    }

    @Test
    @Category(value=UnitTest.class)
    public void testSimpleRoll4() {
        //Randomizer = Collaborator (mocked, stubbed, function, dummy, testDouble)
        //Die = Subject Under Test

        Random randomStub = new Random() {
            @Override
            public int nextInt() {
                return 4;
            }
        };
        Die die = new Die(randomStub);
        Die rolledDie = die.roll();
        assertThat(rolledDie.getPips()).isEqualTo(4);
        assertThat(die.getPips()).isEqualTo(1);
    }

    @Test
    @Category(value=UnitTest.class)
    public void testSimpleRoll2() {
        //Randomizer = Collaborator (mocked, stubbed, function, dummy, testDouble)
        //Die = Subject Under Test

        Random randomStub = new Random() {
            @Override
            public int nextInt() {
                return 2;
            }
        };
        Die die = new Die(randomStub);
        Die rolledDie = die.roll();
        assertThat(rolledDie.getPips()).isEqualTo(2);
        assertThat(die.getPips()).isEqualTo(1);
    }

    @Test
    @Category(value=UnitTest.class)
    public void testSimpleRole2Then5() {
        Random randomMock = createMock(Random.class);
        //Mockito: Random randomMock = mock(Random.class)

        //Rehearsal
        expect(randomMock.nextInt()).andReturn(2).andReturn(5);
        //Mockito: when(...).andReturn(2,5);

        //Replay
        replay(randomMock);

        Die die = new Die(randomMock);
        Die rolled1 = die.roll();
        Die rolled2 = rolled1.roll();

        assertThat(rolled1.getPips()).isEqualTo(2);
        assertThat(rolled2.getPips()).isEqualTo(5);

        //Verify
        verify(randomMock);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Category(value=UnitTest.class)
    public void testThatRandomIsNotNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(Die.RANDOM_IS_NULL);
        new Die(null);
    }

    @Test
    @Category(value=IntegrationTest.class)
    public void testRollAMillionTimeToMakesWeInContraint() {
        Random random = new Random();
        Die die = new Die(random);
        for (int i = 0; i < 1000000; i++) {
           assertThat(die.roll().getPips()).isGreaterThan(0).isLessThan(7);
        }
    }

    @Test
    @Category(value=UnitTest.class)
    public void testBUG3200() {
        Random random = createMock(Random.class);
        expect(random.nextInt(7)).andReturn(4);
        replay(random);

        Die die = new Die(random);
        assertThat(die.roll().getPips()).isGreaterThan(0).isLessThan(7);

        verify(random);
    }

}
