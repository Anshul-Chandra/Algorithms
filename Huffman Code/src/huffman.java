
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class huffman {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {
		
		// ArrayList used in priority queue to create the huffman tree
		ArrayList<Node> lstNodes = new ArrayList<Node>();

		// Hash map created initially to update the frequency of different
		// symbols available
		HashMap<Integer, Integer> symbols = new HashMap<Integer, Integer>();

		// Storing the ASCII value of the symbols in the hash map 'symbols'
		for (int i = 0; i < 256; i++) {
			symbols.put(i, 1);
		}

		// Reading file from Input Stream and using the same to update the
		// frequency of the characters
		BufferedInputStream inputFromFile = new BufferedInputStream(System.in);

		try {
			int character = (int) inputFromFile.read();

			while (character != -1) {
				if(character != 13){
					// characters other than the newline character (13) 
					int freq = symbols.get(character);

					freq++;

					symbols.put(character, freq);					
				}

				character = (int) inputFromFile.read();
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		
		inputFromFile.close();

		// Adding all the symbols to list of nodes
		Set symbolSet = symbols.entrySet();
		Iterator itSymbols = symbolSet.iterator();

		while (itSymbols.hasNext()) {
			Map.Entry ent = (Map.Entry) itSymbols.next();

			lstNodes.add(new Node(Integer.toHexString(Integer.parseInt(ent.getKey().toString())).toUpperCase(), Integer.parseInt(ent.getValue().toString())));
			percolateUp(lstNodes, lstNodes.size() - 1);
		}
		
		
		// Adding an imaginary 'EOF' character; setting up its value as '100' so as to print it at the last of the list
		lstNodes.add(new Node("100", 1));
		
		while (lstNodes.size() > 1) {
			lstNodes.add(new Node(removeSmallestNode(lstNodes), removeSmallestNode(lstNodes)));
			percolateUp(lstNodes, lstNodes.size() - 1);
		}

		// ArrayLIst to hold the huffman codes generated against the different
		// characters
		ArrayList<Node> finalList = new ArrayList<Node>();

		// Traversing the huffman tree to update the codes generated in the
		// 'finalList' arrayList
		TraverseHuffmanTree(lstNodes.get(0), finalList);

		// Sort the 'finalList' on the basis of characters so as to match the
		// sequence of characters in our output as per the 'typical' output
		// provided
		Collections.sort(finalList, Node.ComparatorForNode);

		// Printing the characters along with their huffman codes as the output
		for (Node node : finalList) {

			String str = "";

			if (Integer.valueOf(node.character, 16) > 32 && Integer.valueOf(node.character, 16) < 127)
				str = String.format("%1$3s %2$s", (char) ((int) Integer.valueOf(node.character, 16)), node.huffmanCode);
			else if (Integer.valueOf(node.character, 16) < 16)
				str = String.format("%1$3s %2$s", "0" + node.character, node.huffmanCode);
			else if (Integer.valueOf(node.character, 16) == 256)
				str = String.format("%1$3s %2$s", "EOF", node.huffmanCode); // EOF character
			else
				str = String.format("%1$3s %2$s", node.character, node.huffmanCode);

			System.out.println(str);
		}
	}

	/*
	 * Description : Method defined to traverse the huffman tree with
	 * 'parentNode' as the root node The ArrayList 'lstWithValues' contains the
	 * huffman codes that are fetched while traversing the tree
	 */
	public static void TraverseHuffmanTree(Node parentNode, ArrayList<Node> lstWithValues) {

		if (parentNode.leftNode != null && parentNode.rightNode != null) {
			parentNode.leftNode.huffmanCode = parentNode.huffmanCode + "0";
			parentNode.rightNode.huffmanCode = parentNode.huffmanCode + "1";

			// Traverse the left part of the tree
			TraverseHuffmanTree(parentNode.leftNode, lstWithValues);

			// Traverse the right part of the tree
			TraverseHuffmanTree(parentNode.rightNode, lstWithValues);
		}

		if (parentNode.character.compareToIgnoreCase("") != 0)
			lstWithValues.add(new Node(parentNode.character, parentNode.frequency, parentNode.huffmanCode));
	}

	
	/*
	 * Methods related to trinary min-heap
	 */
	
	// Method to min-heapify a trinary heap (index is zero based index of parent
	// node)
	private static void minHeapify(ArrayList<Node> list, int index) {
		// Checking if the node at 'index' contains any child nodes
		if (hasChildNodes(list, index)) {
			// Get the child elements of the parent node
			int leftChild = getLeftChildNode(index);
			int middleChild = getMiddleChildNode(index);
			int rightChild = getRightChildNode(index);

			int smallest = index;

			if ((list.size() - 1) >= leftChild && list.get(leftChild).frequency < list.get(smallest).frequency)
				smallest = leftChild;

			if ((list.size() - 1) >= middleChild && list.get(middleChild).frequency < list.get(smallest).frequency)
				smallest = middleChild;

			if ((list.size() - 1) >= rightChild && list.get(rightChild).frequency < list.get(smallest).frequency)
				smallest = rightChild;

			if (smallest != index) {
				Node temp = list.get(index);
				list.set(index, list.get(smallest));
				list.set(smallest, temp);

				minHeapify(list, smallest);
			}
		}
	}

	// Method to percolate a value up the heap tree as per min-heap structure
	private static void percolateUp(ArrayList<Node> list, int index) {

		while (index > 0 && list.get(index).frequency < list.get(getParentNode(index)).frequency) {
			int parentNode = getParentNode(index);

			Node temp = list.get(index);
			list.set(index, list.get(parentNode));
			list.set(parentNode, temp);

			// Setting the index and parent node for future iterations
			index = parentNode;
		}
	}

	// Method to remove the smallest element from the list
	private static Node removeSmallestNode(ArrayList<Node> list) throws CloneNotSupportedException {
		// Get the smallest element
		Node smallestElement = null;

		if (list.size() > 0)
			smallestElement = (Node) list.get(0);

		// Copying the last element at the start of the list
		Collections.swap(list, 0, list.size() - 1);

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

	private static boolean hasChildNodes(ArrayList<Node> list, int index) {
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

// Class defined for a node in the huffman tree
class Node {
	public String character; // holds the ASCII value of a character
	public Integer frequency; // holds the frequency of the character in given input
	public String huffmanCode; // holds the huffman code generated for the character

	Node leftNode; // 
	Node rightNode;

	// Default constructor : sets the huffman code to blank string
	public Node() {
		this.huffmanCode = "";
	}

	// Constructor to initialize the node with character and frequency, used when reading input from the file 
	public Node(String character, Integer frequency) {
		this.character = character;
		this.frequency = frequency;
	}

	// Constructor to initialize the node with character, frequency and huffman code, used while traversing the huffman tree
	public Node(String character, Integer frequency, String huffmanCode) {
		this.character = character;
		this.frequency = frequency;
		this.huffmanCode = huffmanCode;
	}

	// Constructor to initialize the list with given left and right node	
	public Node(Node leftNode, Node rightNode) {
		this.leftNode = leftNode;
		this.rightNode = rightNode;

		this.setFrequency();

		this.character = "";
		this.huffmanCode = "";
	}

	public void setFrequency() {
		if (leftNode != null && rightNode != null)
			this.frequency = leftNode.frequency + rightNode.frequency;
	}

	public static Comparator<Node> ComparatorForNode = new Comparator<Node>() {
		public int compare(Node node1, Node node2) {
			return (Integer.valueOf(node1.character, 16)) - (Integer.valueOf(node2.character, 16));
		}
	};
}