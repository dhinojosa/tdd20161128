package com.xyzcorp.tdd;

import java.util.Optional;
import java.util.function.BiFunction;

public class CalcStats {

	private final int[] intArray;

	public CalcStats(int[] intArray) {
		if (intArray == null) throw new IllegalArgumentException("Array cannot be null");
		this.intArray = intArray;
	}

	private Optional<Integer> calculate(BiFunction<Integer, Integer, Integer> biFunction) {
		if (intArray.length == 0)
			return Optional.empty();
		int result = intArray[0];
		for (int i = 1; i < intArray.length; i++) {
            result = biFunction.apply(result, intArray[i]);
		}
		return Optional.of(result);
	}

	public Optional<Integer> getMinimum() {
		return calculate(Math::min);
	}

	public Optional<Integer> getMaximum() {
		return calculate(Math::max);
	}

	public int getSize() {
		return intArray.length;
	}
}
