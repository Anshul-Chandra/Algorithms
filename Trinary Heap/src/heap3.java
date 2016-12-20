
/* 
 * Assigment on Design and Analysis of Algorithms
 * Submitted by	: Anshul Chandra
 * Unity ID 	: achand13
 */

import java.util.ArrayList;
import java.util.Scanner;

public class heap3 {

	public static void main(String[] args) {

		try {
			ArrayList<Integer> list = new ArrayList<Integer>();

			Scanner scan = new Scanner(System.in);

			while (scan.hasNext()) {
				String inputString = scan.nextLine();

				String[] splittedString = inputString.split(" ");

				if (splittedString.length == 2) {
					// case: addition
					if (splittedString[0].toLowerCase().compareTo("add") == 0) {
						int valueToBeAdded = Integer.valueOf(splittedString[1]);

						list.add(valueToBeAdded);
						percolateUp(list, list.size() - 1);
					}
				} else if (splittedString.length == 1) {
					// case: remove
					if (splittedString[0].toLowerCase().compareTo("remove") == 0)
						System.out.println(removeSmallestNode(list));
				}
			}
			
			scan.close();
		} catch (Exception ex) {
			System.out.println("Unable to process.");
		}

	}

	// Method to min-heapify a trinary heap (index is zero based index of parent
	// node)
	private static void minHeapify(ArrayList<Integer> list, int index) {
		// Checking if the node at 'index' contains any child nodes
		if (hasChildNodes(list, index)) {
			// Get the child elements of the parent node
			int leftChild = getLeftChildNode(index);
			int middleChild = getMiddleChildNode(index);
			int rightChild = getRightChildNode(index);

			int smallest = index;

			if ((list.size() - 1) >= leftChild && list.get(leftChild) < list.get(smallest))
				smallest = leftChild;

			if ((list.size() - 1) >= middleChild && list.get(middleChild) < list.get(smallest))
				smallest = middleChild;

			if ((list.size() - 1) >= rightChild && list.get(rightChild) < list.get(smallest))
				smallest = rightChild;

			if (smallest != index) {
				int temp = list.get(index);
				list.set(index, list.get(smallest));
				list.set(smallest, temp);

				minHeapify(list, smallest);
			}
		}
	}

	// Method to percolate a value up the heap tree as per min-heap structure
	private static void percolateUp(ArrayList<Integer> list, int index) {

		while (index > 0 && list.get(index) < list.get(getParentNode(index))) {
			int parentNode = getParentNode(index);

			int temp = list.get(index);
			list.set(index, list.get(parentNode));
			list.set(parentNode, temp);

			// Setting the index and parent node for future iterations
			index = parentNode;
		}
	}

	// Method to remove the smallest element from the list
	private static int removeSmallestNode(ArrayList<Integer> list) {
		// Get the smallest element
		int smallestElement = -1;

		if (list.size() > 0)
			smallestElement = list.get(0);

		// Copying the last element at the start of the list
		list.set(0, list.get(list.size() - 1));

		// removing the last element of the list
		list.remove(list.size() - 1);

		// Min-heapify the list
		minHeapify(list, 0);

		return smallestElement;
	}

	// Method to get the left child node of the parent node in heap
	private static int getLeftChildNode(int index) {
		return ((++index * 3) - 1) - 1;
	}

	// Method to get the middle child node of the parent node in a trinary heap
	// (zero based index)
	private static int getMiddleChildNode(int index) {
		return (++index * 3) - 1;
	}

	// Method to get the right child node of the parent node in trinary heap
	// (zero based index)
	private static int getRightChildNode(int index) {
		return ((++index * 3) + 1) - 1;
	}

	private static boolean hasChildNodes(ArrayList<Integer> list, int index) {
		return (list.size() - 1) >= getLeftChildNode(index) ? true : false;
	}

	// Method to get the index of parent node of a child node in a trinary heap
	// (zero based index)
	private static int getParentNode(int index) {
		int remainder = ++index % 3;

		switch (remainder) {
		case 2:
			return (int) Math.floor((double) (index / 3));
		case 0:
			return (index / 3) - 1;
		case 1:
			return (int) Math.ceil((double) (index / 3)) - 1;
		default:
			return -1;
		}
	}
}
