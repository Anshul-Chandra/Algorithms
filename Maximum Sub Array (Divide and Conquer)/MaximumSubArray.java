package programs;

class Indexes{
	int indexNumber;
}

public class MaximumSubArray {

	// Main function
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arrayToWork = { -7, -8, 4, -10, -6, 10, 13 };
		
		int startIndex, endIndex, sum;
		
		startIndex = 0;
		endIndex = arrayToWork.length - 1;
		
		Indexes SubArrayStartIndex 	= new Indexes();
		Indexes SubArrayEndIndex 	= new Indexes();
		
		// Getting the maximum sub-array
		sum = GetMaximumSubArray(arrayToWork, startIndex, endIndex, SubArrayStartIndex, SubArrayEndIndex);
		
		System.out.println("The sum of the maximum sub-array is = " + sum);
		
		// Print the values of the array
		PrintValues(arrayToWork, SubArrayStartIndex.indexNumber, SubArrayEndIndex.indexNumber);
	}
	
	public static int GetMaximumSubArray(int[] arrayToWork, int startIndex, int endIndex, Indexes SubArrayStartIndex, Indexes SubArrayEndIndex){
		int midIndex, subArraySum, subArrayLeftSum, subArrayRightSum, subArrayCrossSum;
		
		subArraySum 		= 0;
		subArrayLeftSum 	= 0;
		subArrayRightSum 	= 0;
		subArrayCrossSum 	= 0;
		
		Indexes startIndexForLeftSubArray 	= new Indexes();
		Indexes endIndexForLeftSubArray 	= new Indexes();
		
		Indexes startIndexForRightSubArray 	= new Indexes();
		Indexes endIndexForRightSubArray 	= new Indexes();
		
		Indexes startIndexForCrossSubArray 	= new Indexes();
		Indexes endIndexForCrossSubArray 	= new Indexes();
		
		if(startIndex == endIndex){
			// case when only one element is present in the array
			SubArrayStartIndex.indexNumber	= startIndex;
			SubArrayEndIndex.indexNumber	= endIndex;
			subArraySum 					= arrayToWork[startIndex]; 
		}
		else {
			midIndex = (startIndex + endIndex) / 2;
			
			// searching for the maximum sub-array in the first part of the array
			subArrayLeftSum = GetMaximumSubArray(arrayToWork, startIndex, midIndex, startIndexForLeftSubArray, endIndexForLeftSubArray);
			
			// searching for the maximum sub-array in the first part of the array
			subArrayRightSum = GetMaximumSubArray(arrayToWork, midIndex + 1, endIndex, startIndexForRightSubArray, endIndexForRightSubArray);
			
			// searching for the maximum sub-array in the cross-array
			subArrayCrossSum = GetCrossMaxSubArray(arrayToWork, startIndex, midIndex, endIndex, startIndexForCrossSubArray, endIndexForCrossSubArray);
			
		}
		
		if(subArraySum == 0){
			if(subArrayLeftSum >= subArrayRightSum && subArrayLeftSum >= subArrayCrossSum) {
				subArraySum = subArrayLeftSum;
				SubArrayStartIndex.indexNumber = startIndexForLeftSubArray.indexNumber;
				SubArrayEndIndex.indexNumber = endIndexForLeftSubArray.indexNumber;
			}
			else if(subArrayRightSum >= subArrayLeftSum && subArrayRightSum >= subArrayCrossSum){
				subArraySum = subArrayRightSum;
				SubArrayStartIndex.indexNumber = startIndexForRightSubArray.indexNumber;
				SubArrayEndIndex.indexNumber = endIndexForRightSubArray.indexNumber;
			}			
			else if(subArrayCrossSum >= subArrayLeftSum && subArrayCrossSum >= subArrayRightSum){
				subArraySum = subArrayCrossSum;
				SubArrayStartIndex.indexNumber = startIndexForCrossSubArray.indexNumber;
				SubArrayEndIndex.indexNumber = endIndexForCrossSubArray.indexNumber;
			}
		}			
		
		return subArraySum;
	}
		
	// Method to find the maximum sub-array present across a mid point in a given array
	public static int GetCrossMaxSubArray(int[] arr, int startIndex, int midIndex, int endIndex, Indexes SubArrayStartIndex, Indexes SubArrayEndIndex) {
		int sum, leftSum, rightSum;
		int leftIndex, rightIndex;
		
		sum 		= 0;
		leftSum 	= arr[midIndex];
		rightSum 	= arr[midIndex + 1];
		
		
		leftIndex	= midIndex;
		rightIndex	= endIndex;		
		
		// Getting the maximum sub-array for left part of 'midIndex'
		for(int i = midIndex; i >= 0; i--){
			sum += arr[i];
			
			if(leftSum <= sum){
				leftSum = sum;
				leftIndex = i;
			}
		}
		
		sum = 0;
		
		// Getting the maximum sub-array for right part of 'midIndex'
		for(int i = midIndex + 1; i <= endIndex; i++){
			sum += arr[i];
			
			if(rightSum <= sum){
				rightSum = sum;
				rightIndex = i;
			}
		}
		
		sum 							= leftSum + rightSum; 
		SubArrayStartIndex.indexNumber 	= leftIndex;
		SubArrayEndIndex.indexNumber 	= rightIndex;
		
		
		return sum;
	}
	
	//Method to print the array items as per start and end index provided 
	public static void PrintValues(int[] arr, int startIndex, int endIndex) {
		System.out.println();

		for(int i = startIndex; i <= endIndex; i++)
		{
			System.out.print(" | " + arr[i]);
		}

		System.out.print(" |");
	}

}
