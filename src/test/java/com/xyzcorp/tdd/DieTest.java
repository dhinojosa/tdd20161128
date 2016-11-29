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
        Die die = new DieImpl(createMock(Random.class)); //Reliant Randomizer
        assertThat(die.getPips()).isEqualTo(1);
    }

    @Test
    @Category(value=UnitTest.class)
    public void testSimpleRoll4() {
        //Randomizer = Collaborator (mocked, stubbed, function, dummy, testDouble)
        //Die = Subject Under Test

        Random randomStub = new Random() {
            @Override
            public int nextInt(int bound) {
                return 3;
            }
        };
        Die die = new DieImpl(randomStub);
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
            public int nextInt(int bound) {
                return 1;
            }
        };
        Die die = new DieImpl(randomStub);
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
        expect(randomMock.nextInt(6)).andReturn(1).andReturn(4);
        //Mockito: when(...).thenReturn(2,5);

        //Replay
        replay(randomMock);

        //-------------------This where the test resides----------
        Die die = new DieImpl(randomMock);
        Die rolled1 = die.roll();
        Die rolled2 = rolled1.roll();

        assertThat(rolled1.getPips()).isEqualTo(2);
        assertThat(rolled2.getPips()).isEqualTo(5);
        //-------------------This is where the test ends----------


        //Verify
        verify(randomMock);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Category(value=UnitTest.class)
    public void testThatRandomIsNotNull() throws Exception {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(DieImpl.RANDOM_IS_NULL);
        new DieImpl(null);
    }

    @Test
    @Category(value=IntegrationTest.class)
    public void testRollAMillionTimeToMakesWeInContraint() {
        Random random = new Random();
        Die die = new DieImpl(random);
        for (int i = 0; i < 1000000; i++) {
           assertThat(die.roll().getPips()).isGreaterThan(0).isLessThan(7);
        }
    }

    @Test
    @Category(value=UnitTest.class)
    public void testBUG3200() {
        Random random = createMock(Random.class);
        expect(random.nextInt(6)).andReturn(4);
        replay(random); //Marking mock to run
        Die die = new DieImpl(random);
        assertThat(die.roll().getPips()).isGreaterThan(0).isLessThan(7);
        verify(random);
    }

    @Test
    @Category(value=UnitTest.class)
    public void testBUG3200WithZero() {
        Random random = createMock(Random.class);
        expect(random.nextInt(6)).andReturn(0);
        replay(random); //Marking mock to run
        Die die = new DieImpl(random);
        assertThat(die.roll().getPips()).isGreaterThan(0).isLessThan(7);
        verify(random);
    }
}
