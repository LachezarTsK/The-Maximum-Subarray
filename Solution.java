import java.util.Scanner;

/**
 * Time complexity of the solution: O(n).
 */
public class Solution {
	/**
	 * An arrays of two elements, where:
	 * 
	 * array[0]=maxSubarray
	 * 
	 * array[1]=maxSubsequence
	 */
	private static int[] maxSubarray_maxSubsequence;

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		int numberOfTestCases = reader.nextInt();
		for (int i = 0; i < numberOfTestCases; i++) {
			int numberOfElements = reader.nextInt();
			int[] elements = new int[numberOfElements];
			for (int j = 0; j < numberOfElements; j++) {
				int element = reader.nextInt();
				elements[j] = element;
			}
			System.out.println(maxSubarray(elements)[0] + " " + maxSubarray(elements)[1]);
		}
		reader.close();
	}

	/**
	 * @param inputArray.
	 * @return array "maxSubarray_maxSubsequence".
	 */
	private static int[] maxSubarray(int[] elements) {
		maxSubarray_maxSubsequence = new int[2];
		int[] indexOfFirstAndLastPositive = findIndexOfFirstAndLastPositive_ifAllNonPositiveThenGetMax(elements);

		/**
		 * If there are only non-positive elements, then return maximum
		 * subarray/subsequence.
		 */
		if (indexOfFirstAndLastPositive == null) {
			return maxSubarray_maxSubsequence;
		}

		int startIndex = indexOfFirstAndLastPositive[0];
		int endIndex = indexOfFirstAndLastPositive[1];
		int index = startIndex;
		int positiveSet = 0;
		int negativeSet = 0;

		/**
		 * Gets the first positive set of integers.
		 */
		while (index <= endIndex && elements[index] >= 0) {
			positiveSet += elements[index];
			index++;
		}

		int currentSubarray = positiveSet;
		int maxSubsequence = positiveSet;
		int maxSubarray = currentSubarray;

		while (index <= endIndex) {

			negativeSet = 0;
			positiveSet = 0;
			/**
			 * Gets the next negative set of integers.
			 */
			while (index <= endIndex && elements[index] < 0) {
				negativeSet += elements[index];
				index++;
			}
			/**
			 * Gets the next positive set of integers.
			 */
			while (index <= endIndex && elements[index] >= 0) {
				positiveSet += elements[index];
				index++;
			}

			/**
			 * If the continuation of the current subarray results in a lower value than the
			 * current one, then start a new subarray, which first element is the value of
			 * the current positive set.
			 */
			if (currentSubarray + positiveSet + negativeSet < positiveSet) {
				currentSubarray = positiveSet;
				maxSubarray = Math.max(maxSubarray, currentSubarray);
			}
			/**
			 * Otherwise, continue current subarray.
			 */
			else {
				currentSubarray += positiveSet + negativeSet;
				maxSubarray = Math.max(maxSubarray, currentSubarray);
			}
			maxSubsequence += positiveSet;
		}

		maxSubarray_maxSubsequence[0] = maxSubarray;
		maxSubarray_maxSubsequence[1] = maxSubsequence;
		return maxSubarray_maxSubsequence;
	}

	/**
	 * Checks for leading and trailing non-positive elements in the input array.
	 * 
	 * Finds the index of the first and the last positive element, in case it
	 * contains positive element(s).
	 * 
	 * If all elements in the input array are non-positive, calculates the maximum
	 * subarray/subsequence.
	 * 
	 * @param inputArray.
	 * @return array of integers or null.
	 * 
	 *         If there is at least one positive element, returns an array of two
	 *         elements, where array[0]="index of first positive element", and
	 *         array[1]="index of last positive element".
	 * 
	 *         If all elements are non-positive, calculates the maximum
	 *         subarray/subsequence and stores the results in a static instance
	 *         variable, array "maxSubarray_maxSubsequence".
	 */
	private static int[] findIndexOfFirstAndLastPositive_ifAllNonPositiveThenGetMax(int[] elements) {
		int index = 0;
		int nonPositiveMax = (int) -Math.pow(10, 4);
		int[] indexOfFirstAndLastPositive = new int[2];

		/**
		 * Checks for leading non-positive elements.
		 */
		while (index < elements.length && elements[index] <= 0) {
			nonPositiveMax = Math.max(nonPositiveMax, elements[index]);
			index++;
		}

		/**
		 * If the loop that checks for leading non-positive elements iterates through
		 * the whole input array, then all the elements are non-positive.
		 */
		if (index == elements.length) {
			maxSubarray_maxSubsequence[0] = nonPositiveMax;
			maxSubarray_maxSubsequence[1] = nonPositiveMax;
			return null;
		}

		/**
		 * Index of first positive element.
		 */
		indexOfFirstAndLastPositive[0] = index;
		index = elements.length - 1;

		/**
		 * Checks for trailing non-positive elements.
		 */
		while (index >= 0 && elements[index] <= 0) {
			index--;
		}

		/**
		 * Index of last positive element.
		 */
		indexOfFirstAndLastPositive[1] = index;

		return indexOfFirstAndLastPositive;
	}
}
