
/* 
 * Assigment on Design and Analysis of Algorithms
 * Submitted by	: Anshul Chandra
 * Unity ID 	: achand13
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class qsort {

	// variable to hold the base case value to switch to insertion sort when the
	// array size becomes less than or equal to 'k' defined below
	static int k = 0;

	static boolean isRandomized = true;

	public static void main(String[] args) {

		try {
			ArrayList<Integer> arr = new ArrayList<Integer>();

			Scanner scan = new Scanner(System.in);

			// Adding the numbers from standard input into arraylist
			while (scan.hasNext()) {
				int num = scan.nextInt();

				arr.add(num);
			}

			scan.close();

			Integer[] arrToSort = new Integer[arr.size()];

			arrToSort = arr.toArray(arrToSort);

			// Checking for command line argument
			if (args != null && args.length > 0)
				if (tryParseInt(args[0]))
					k = Integer.valueOf(args[0]);

			long startTime = System.currentTimeMillis();

			// Start the Partitioning
			QuickSort(arrToSort, 0, arrToSort.length - 1);

			long endTime = System.currentTimeMillis();

			System.err.println("Total execution time = " + (endTime - startTime));

			// Print the sorted array
			PrintArray(arrToSort);
		} catch (Exception ex) {
			System.out.println("Unable to process");
		}

	}

	// Method to QuickSort an array (using randomized and non-randomized method)
	public static void QuickSort(Integer[] arr, int startIndex, int endIndex) {
		if (endIndex - startIndex + 1 > k) {
			// Sorting the list with quicksort if no. of elements > 'k'
			if (startIndex < endIndex) {
				// Get the position of pivot
				int pivotPosition = Partition(arr, startIndex, endIndex);

				// Carry out the same process on either side of the pivot
				QuickSort(arr, startIndex, pivotPosition - 1);
				QuickSort(arr, pivotPosition + 1, endIndex);
			}
		} else {
			// sort the array using Insertion sort for no. of elements <= k
			insertionSort(arr, startIndex, endIndex);
		}
	}

	// Method to partition the array on the basis of a pivot and return its
	// position
	public static int Partition(Integer[] arr, int startIndex, int endIndex) {
		int i = startIndex - 1;
		int pivot = arr[endIndex];

		if (isRandomized) {
			Random randomizer = new Random();

			// Generate a random number from the range of startIndex and
			// endIndex
			int randomNumber = randomizer.nextInt((endIndex - startIndex) + 1) + startIndex;

			// swap the randomized element and the last element
			int temp = arr[randomNumber];
			arr[randomNumber] = arr[endIndex];
			arr[endIndex] = temp;

			pivot = arr[endIndex];
		}

		int j = startIndex;

		while (j <= endIndex) {
			if (arr[j] <= pivot) {
				i++;
				// Swapping the contents of arr[j] with arr[i]
				int temp = arr[j];
				arr[j] = arr[i];
				arr[i] = temp;
			}

			j++;
		}

		return i;
	}

	// Method to print the contents of an array
	private static void PrintArray(Integer[] arr) {
		for (int i = 0; i < arr.length; i++)
			System.out.println(arr[i]);
	}

	// Method to check if string can be converted to integer
	private static boolean tryParseInt(String strToParse) {
		try {
			Integer.valueOf(strToParse);

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	// Method to sort an array using Insertion Sort
	private static void insertionSort(Integer[] arr, int startIndex, int endIndex) {
		for (int i = startIndex + 1; i <= endIndex; i++) {
			int val = arr[i];

			int j = i - 1;

			while (j >= 0 && val < arr[j]) {
				arr[j + 1] = arr[j];
				j--;
			}

			arr[j + 1] = val;
		}
	}
}