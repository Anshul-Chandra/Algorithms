import java.util.Random;

public class Quicksort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arrToSort = new int[] { 4, 2, 3, 7, 10 , 7, 4, 6, 5, 8, 123, 1000, 323, -12, -324, 0 }; 

		// Start the Partitioning
		QuickSort(arrToSort, 0, arrToSort.length - 1, true);

		// Print the sorted array
		PrintArray(arrToSort);
	}

	// Method to QuickSort an array (using randomized and non-randomized method)
	public static void QuickSort(int[] arr, int startIndex, int endIndex, boolean isRandomized) {

		if (startIndex < endIndex) {
			// Get the position of pivot
			int pivotPosition = Partition(arr, startIndex, endIndex, isRandomized);

			// Carry out the same process on either side of the pivot
			QuickSort(arr, startIndex, pivotPosition - 1, isRandomized);
			QuickSort(arr, pivotPosition + 1, endIndex, isRandomized);
		}

	}

	// Method to partition the array on the basis of a pivot and return its position
	public static int Partition(int[] arr, int startIndex, int endIndex, boolean isRandomized) {
		int i = startIndex - 1;
		int pivot = arr[endIndex];

		if(isRandomized){
			Random randomizer = new Random();
			
			// Generate a random number from the range of startIndex and endIndex
			int randomNumber = randomizer.nextInt((endIndex - startIndex) + 1) + startIndex;
			
			// swap the randomized element and the last element
			int temp 			= arr[randomNumber];
			arr[randomNumber] 	= arr[endIndex];
			arr[endIndex] 		= temp;
			
			pivot 				= arr[endIndex];
		}
		
		int j = startIndex;

		while (j <= endIndex) {
			if (arr[j] <= pivot) {
				i++;
				// Swapping the contents of arr[j] with arr[i]
				int temp 	= arr[j];
				arr[j] 		= arr[i];
				arr[i] 		= temp;
			}
			
			j++;
		}
		
		return i;
	}

	// Method to print the contents of an array
	public static void PrintArray(int[] arr) {
		System.out.println("The sorted array is as follows - ");
		System.out.print(arr[0]);
		for (int i = 1; i < arr.length; i++)
			System.out.print(", " + arr[i]);
	}
}
