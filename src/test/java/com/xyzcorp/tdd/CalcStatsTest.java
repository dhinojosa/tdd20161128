package com.xyzcorp.tdd;

import org.assertj.core.data.Offset;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Optional;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Category(value = {UnitTest.class})
public class CalcStatsTest {

	private static CalcStats emptyArray;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeAnyTestsRun() {
		System.out.println("Before class");
		emptyArray = new CalcStats(new int[] {});
	}

	@AfterClass
	public static void setUpAfterAnyTestsRun() {
		System.out.println("After class");
	}

	@Before
	public void setUp() {
		System.out.println("Before method");
	}

	@After
	public void tearDown() {
		System.out.println("After method");
	}

	@Test 
	public void testMinimumFromAnArrayOfOneItem() {
		// 0. Is there something already created that this should belong to?
		// 1. Find the simplest thing that I can test.
		// 2. How do I want to make this thing?
		// a. new Instance and provide array at const.
		// CalcStats cs = new CalcStats(new int[]{1});
		// int m = cs.getMinimum()
		// b. new Instance and use setter
		// CalcStats cs = new CalcStats();
		// cs.setArray(new int[]{1});
		// int m = cs.getMinimum
		// c. static method:
		// int m = CalcStats.getMinimum(new int[]{1,2,3,4,5});

		CalcStats cs = new CalcStats(new int[] { 1 });
		Optional<Integer> result = cs.getMinimum();
		assertEquals(Optional.of(1), result);
	}

	@Test
	public void testMinimumFromAnArrayOfNoItems() {
		// 1. Exception (IllegalArgument) (Not a good idea)
		// 2. null
		// 3. java.util.Optional

		Optional<Integer> result = emptyArray.getMinimum();
		assertEquals(Optional.empty(), result);
	}

	@Test
	@Category(value = {AwesomeTest.class})
	public void testMinimumFromAnArrayOfTwoItemsMinFirst() {
		CalcStats cs = new CalcStats(new int[] { 3, 10 });
		assertEquals(Optional.of(3), cs.getMinimum());
	}

	@Test
	public void testMinimumFromAnArrayOfTwoItemsMaxFirst() {
		CalcStats cs = new CalcStats(new int[] { 10, 3 });
		assertEquals(Optional.of(3), cs.getMinimum());
	}

	// NotRed
	@Test
	public void testMinimumFromAnArrayOfFiveSameNumbers() {
		CalcStats cs = new CalcStats(new int[] { 10, 10, 10, 10, 10 });
		assertEquals(Optional.of(10), cs.getMinimum());
	}

	// NotRed
	@Test
	public void testMinimumFromAnArrayOfFiveZeros() {
		CalcStats cs = new CalcStats(new int[] { 0, 0, 0, 0, 0 });
		assertEquals(Optional.of(0), cs.getMinimum());
	}

	// NotRed
	@Test
	public void testMinimumFromAnArrayOfSixComboNegativePositiveNumbersAndZero() {
		CalcStats cs = new CalcStats(new int[] { 1, -4, 10, 923, 0, 4 });
		assertEquals(Optional.of(-4), cs.getMinimum());
	}

	@Test
	public void testMinimumFromAnArrayWhereArrayIsNullClassicForm() {
		try {
			new CalcStats(null);
			fail("This line should not even be reached");
		} catch (IllegalArgumentException iae) {
			assertEquals("Array cannot be null", iae.getMessage());
		}
	}



	@Test
	public void testMinimumFromAnArrayWhereArrayIsNullRuleForm() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Array cannot be null");
		new CalcStats(null);
	}

	@Test
	public void testMaximumFromAnArrayOfOneItem() {
		CalcStats cs = new CalcStats(new int[] { 1 });
		assertThat(cs.getMaximum()).contains(1);
		assertThat(cs.getMaximum()).isEqualTo(Optional.of(1));
	}

	@Test
	public void testMaximumFromAnArrayOfNoItems() {
		assertThat(emptyArray.getMaximum()).isEmpty();
	}

	@Test
	public void testMaximumFromAnArrayOfTwoItemsMaxFirst() {
		CalcStats cs = new CalcStats(new int[] { 10, 3 });
		assertThat(cs.getMaximum()).contains(10);
	}

	@Test
	public void testMaximumFromAnArrayOfTwoItemsMinFirst() {
		CalcStats cs = new CalcStats(new int[] { 3, 10 });
		assertThat(cs.getMaximum()).contains(10);
	}

	public Integer foo(BiFunction<String, String, Integer> compareFunction) {
		return compareFunction.apply("Foo", "Bar");
	}

	@Test
	public void testTypeIdentityWithFunction() {
		//System.out.println(foo(String::compareTo));
	}

	@Test
	public void testSizeWithZeroItems() {
        assertThat(emptyArray.getSize()).isEqualTo(0);
	}

	@Test
	public void testSizeFromAnArrayOfOneItem() {
		CalcStats cs = new CalcStats(new int[] { 1 });
		assertThat(cs.getSize()).isEqualTo(1);
	}

	@Test
	public void testAverageFromAnArrayOfOneItem() {
		CalcStats cs = new CalcStats(new int[] { 100 });
		assertThat(cs.getAverage()).isEqualTo(Optional.of(100.0));
	}
	
	@Test
	public void testAverageFromAnArrayOfTwoItemEven() {
		CalcStats cs = new CalcStats(new int[] { 0, 100 });
		assertThat(cs.getAverage()).isEqualTo(Optional.of(50.0));
	}
	
	@Test
	public void testAverageFromArrayOfThreeItemsThatEqual100DividedByThree() {
		CalcStats cs = new CalcStats(new int[] { 0, 50, 50});
		assertThat(cs.getAverage()).hasValueSatisfying
				(d ->  assertThat(d).isEqualTo(33.33, Offset.offset(.01)));
	}
	
	@Test
	public void testAverageFromArrayOfZeroItems() {
		assertThat(emptyArray.getAverage()).isEqualTo(Optional.empty());
	}

	//NoRed
	@Test
	public void testAverageFromArrayOfTwoItemsThatContainANegative() {
		CalcStats cs = new CalcStats(new int[] { -100, 100});
		assertThat(cs.getAverage()).contains(0.0);
	}
	
}
