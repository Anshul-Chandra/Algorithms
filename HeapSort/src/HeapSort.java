
public class HeapSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arrayForHeap = new int[] { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7, 100 };

		// Print the array before heap sort
		System.out.println("The contents of the array before Heap Sort -");
		PrintArray(arrayForHeap);

		// Heap-Sort the array
		StartHeapSort(arrayForHeap);
		
		// Print the array after heap sort
		System.out.println("\nThe contents of the array after After Heap Sort -");
		PrintArray(arrayForHeap);
	}
	
	// Method to heap sort an array Complexity = O(n ln n)
	public static void StartHeapSort(int[] arr){
		int heapSize = arr.length;
		
		// build max-heap for the current heapSize
		BuildMaxHeap(arr, heapSize);
		// Move the greatest element (i.e. at position arr[0]) to last
		int temp 			= arr[0];
		arr[0] 				= arr[heapSize - 1];
		arr[heapSize - 1] 	= temp;
		
		heapSize--;
		
		while(heapSize > 1){
			// Max-heapify the element at 0 as all the child nodes are already max-heapified
			MaxHeapify(arr, 0, heapSize);
			
			// Move the greatest element (i.e. at position arr[0]) to last
			temp 				= arr[0];
			arr[0] 				= arr[heapSize - 1];
			arr[heapSize - 1] 	= temp;
			
			// decrement the heapSize by 1
			heapSize--;
		}
	}
	
	// Method to build a max-heap from an array; Complexity = O(n)
	public static void BuildMaxHeap(int[] arr, int heapSize){
		
		int lastLeafNode = (int)Math.ceil((double)arr.length / 2);
		
		
		for(int i = lastLeafNode; i >= 0; i--)
			MaxHeapify(arr, i, heapSize);
	}
	
	// Method to max-heapify an array at an index - Complexity = O(ln n)
	public static void MaxHeapify(int[] arr, int index, int heapSize) {
		int largest = -1; // variable to hold the index of the largest of the
							// child nodes

		int leftChild = GetLeftChild(index);
		int rightChild = GetRightChild(index);
		
		if (leftChild < heapSize && arr[leftChild] > arr[index])
			largest = leftChild;
		else
			largest = index;

		if (rightChild < heapSize && arr[rightChild] > arr[largest])
			largest = rightChild;

		if (largest != -1 && largest != index) {
			// swap the elements at index = 'index' and 'largest'
			int temp = arr[index];
			arr[index] = arr[largest];
			arr[largest] = temp;

			// Recurse till the element at index = 'index' reaches the correct
			// level
			MaxHeapify(arr, largest, heapSize);
		}
	}

	// Method to get the index of the left child node in a heap - Works on zero based index
	public static int GetLeftChild(int index) {
		return ((index + 1) * 2) - 1;
	}

	// Method to get the index of the right child node in a heap - Works on zero based index
	public static int GetRightChild(int index) {
		return ((index + 1) * 2);
	}

	// Method for printing the values of the array
	public static void PrintArray(int[] arr) {
		System.out.print(arr[0]);
		for (int i = 1; i < arr.length; i++)
			System.out.print(", " + arr[i]);
	}

}
