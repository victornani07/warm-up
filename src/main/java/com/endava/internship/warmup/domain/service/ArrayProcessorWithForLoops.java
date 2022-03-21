package com.endava.internship.warmup.domain.service;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayProcessorWithForLoops implements ArrayProcessor {

    /**
     * Return true if there are no numbers that divide by 10
     * @param input non-null immutable array of ints
     */
    @Override
    public boolean noneMatch(final int[] input) {
        for(int num : input) {
            if (num % 10 == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return true if at least one value in input matches the predicate
     * @param input non-null immutable array of ints
     * @param predicate invoke the predicate.test(int value) on each input element
     */
    @Override
    public boolean someMatch(final int[] input, IntPredicate predicate) {
        for(int num : input) {
            if (predicate.test(num)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if all values processed by function, matches the predicate
     * @param input non-null immutable array of Strings. No element is null
     * @param function invoke function.applyAsInt(String value) to transform all the input elements into an int value
     * @param predicate invoke predicate.test(int value) to test the int value obtained from the function
     */
    @Override
    public boolean allMatch(final String[] input,
                            ToIntFunction<String> function,
                            IntPredicate predicate) {
        for(String s : input) {
            int num = function.applyAsInt(s);

            if(!predicate.test(num)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Copy values into a separate array from specific index to stopindex
     * @param input non-null array of ints
     * @param startInclusive the first index of the element from input to be included in the new array
     * @param endExclusive the last index prior to which the elements are to be included in the new array
     * @throws IllegalArgumentException when parameters are outside of input index bounds
     */
    @Override
    public int[] copyValues(int[] input, int startInclusive, int endExclusive) throws IllegalArgumentException {
        if(startInclusive < 0 || endExclusive >= input.length || startInclusive >= endExclusive) {
            throw new IllegalArgumentException();
        }

        return Arrays.stream(input, startInclusive, endExclusive)
                .toArray();
    }

    /**
     * Replace even index values with their doubles and odd indexed elements with their negative
     * @param input non-null immutable array of ints
     * @return new array with changed elements
     */
    @Override
    public int[] replace(final int[] input) {
        return IntStream.range(0, input.length)
                .map(i -> i % 2 == 0 ? 2 * input[i] : -input[i])
                .toArray();
    }

    /**
     * Find the second max value in the array
     * @param input non-null immutable array of ints
     */
    @Override
    public int findSecondMax(final int[] input) {
        int[] newValues = Arrays.stream(input)
                .sorted()
                .distinct()
                .toArray();

        return newValues[newValues.length - 2];
    }

    /**
     * Return in reverse first negative numbers, then positive numbers from array
     * @param input non-null immutable array of ints.
     * @return example: input {3, -5, 4, -7, 2 , 9}
     *                  result: {-7, -5, 9, 2, 4, 3}
     */
    @Override
    public int[] rearrange(final int[] input) {
        int[] newValues = new int[input.length];

        int i = 0, j = (int)Arrays.stream(input)
                .filter(n -> n < 0)
                .count();

        for(int k = input.length - 1; k >= 0; --k) {
            if (input[k] < 0) {
                newValues[i++] = input[k];
            } else {
                newValues[j++] = input[k];
            }
        }

        return newValues;
    }

    /**
     * Remove (filter) all values which are smaller than (input max element - 10)
     * @param input non-null immutable array of ints
     * @return The result array should not contain empty cells!
     */
    @Override
    public int[] filter(final int[] input) {
        int threshold = input.length - 10;

        return Arrays.stream(input)
                .filter(n -> n >= threshold)
                .toArray();
    }

    /**
     * Insert values into input array at a specific index.
     * @param input non-null immutable array of ints.
     * @param startInclusive the index of input at which the first element from values array should be inserted
     * @param values the values to be inserted from startInclusive index
     * @return new array containing the combined elements of input and values
     * @throws IllegalArgumentException when startInclusive is out of bounds for input
     */
    @Override
    public int[] insertValues(final int[] input, int startInclusive, int[] values) throws IllegalArgumentException {
        if(startInclusive < 0 || startInclusive >= input.length) {
            throw new IllegalArgumentException();
        }

        int[] newValues = new int[input.length + values.length];
        int j = 0;

        for(int i = 0; i < input.length; ++i) {
            if(i == startInclusive) {
                while (j < values.length) {
                    newValues[i + j] = values[j++];
                }
            }

            newValues[i + j] = input[i];
        }

        return newValues;
    }

    /**
     * Merge two sorted input and input2 arrays so that the return values are also sorted
     * @param input first non-null array
     * @param input2 second non-null array
     * @return new array containing all elements sorted from input and input2
     * @throws IllegalArgumentException if either input or input are not sorted ascending
     */
    @Override
    public int[] mergeSortedArrays(int[] input, int[] input2) throws IllegalArgumentException {
        final int N = input.length;
        final int M = input2.length;
        int[] newValues = new int[N + M];
        int i = 0, j = 0, k = 0;

        while(i < N && j < M) {
            if(input[i] < input2[j]) {
                if(i > 0 && input[i] < input[i - 1]) {
                    throw new IllegalArgumentException();
                }

                newValues[k++] = input[i++];
            } else {
                if(j > 0 && input2[j] < input2[j - 1]) {
                    throw new IllegalArgumentException();
                }

                newValues[k++] = input2[j++];
            }
        }

        while(i < N) {
            if(i > 0 && input[i] < input[i - 1]) {
                throw new IllegalArgumentException();
            }

            newValues[k++] = input[i++];
        }

        while(j < M) {
            if(j > 0 && input2[j] < input2[j - 1]) {
                throw new IllegalArgumentException();
            }

            newValues[k++] = input2[j++];
        }

        return newValues;
    }

    /**
     * In order to execute a matrix multiplication, in this method, please validate the input data throwing exceptions for invalid input. If the the
     * input params are satisfactory, do not throw any exception.
     *
     * Please review the matrix multiplication https://www.mathsisfun.com/algebra/matrix-multiplying.html
     * @param leftMatrix the left matrix represented by array indexes [row][column]
     * @param rightMatrix the right matrix represented by array indexes [row][column]
     * @throws NullPointerException when any of the inputs are null. (arrays, rows and columns)
     * @throws IllegalArgumentException when any array dimensions are not appropriate for matrix multiplication
     */
    @Override
    public void validateForMatrixMultiplication(int[][] leftMatrix, int[][] rightMatrix) throws NullPointerException, IllegalArgumentException {
        if(leftMatrix == null || rightMatrix == null) {
            throw new NullPointerException();
        }

        for(int[] row : leftMatrix) {
            if (row == null) {
                throw new NullPointerException();
            }
        }

        for(int[] row : rightMatrix) {
            if (row == null) {
                throw new NullPointerException();
            }
        }

        if(leftMatrix.length == 0 && rightMatrix.length == 0) {
            throw new IllegalArgumentException();
        }

        int numberOfColumnsForLeftMatrix = leftMatrix[0].length;
        int numberOfColumnsForRightMatrix = rightMatrix[0].length;
        int numberOfRowsForRightMatrix = rightMatrix.length;

        for(int i = 1; i < leftMatrix.length; ++i) {
            if (leftMatrix[i].length != numberOfColumnsForLeftMatrix) {
                throw new IllegalArgumentException();
            }
        }

        for(int i = 1; i < rightMatrix.length; ++i) {
            if (rightMatrix[i].length != numberOfColumnsForRightMatrix) {
                throw new IllegalArgumentException();
            }
        }

        if(numberOfColumnsForLeftMatrix != numberOfRowsForRightMatrix) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Perform the matrix multiplication as described in previous example Please review the matrix multiplication
     * https://www.mathsisfun.com/algebra/matrix-multiplying.html
     * @param leftMatrix the left matrix represented by array indexes [row][column]
     * @param rightMatrix the right matrix represented by array indexes [row][column]
     * @throws NullPointerException when any of the inputs are null. (arrays, rows and columns)
     * @throws IllegalArgumentException when any array dimensions are not appropriate for matrix multiplication
     */
    @Override
    public int[][] matrixMultiplication(final int[][] leftMatrix, final int[][] rightMatrix) throws NullPointerException, IllegalArgumentException {
        validateForMatrixMultiplication(leftMatrix, rightMatrix);

        int M = leftMatrix.length, N = rightMatrix[0].length;
        int[][] multiplicationMatrix = new int[M][N];

        for(int k = 0; k < leftMatrix.length; ++k) {
            for(int j = 0; j < rightMatrix[0].length; ++j) {
                for (int i = 0; i < rightMatrix.length; ++i) {
                    multiplicationMatrix[k][j] += (leftMatrix[k][i] * rightMatrix[i][j]);
                }
            }
        }

        return multiplicationMatrix;
    }

    /**
     * Return only distinct values in an array.
     * @param input non-null immutable array of ints.
     */
    @Override
    public int[] distinct(final int[] input) {
        Set<Integer> s = Arrays.stream(input)
                .boxed()
                .collect(Collectors.toSet());

        return s.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
