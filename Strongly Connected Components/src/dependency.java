
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class dependency {

	static int time;

	public static void main(String[] args) throws FileNotFoundException {

		time = 0;

		Scanner scan = new Scanner(System.in);

		int numberOfComponents = scan.nextInt();

		// Array that holds all the components
		Node[] components = new Node[numberOfComponents];

		// Adjacency Matrix
		int[][] adjMat = new int[numberOfComponents][numberOfComponents];

		int cnt = 0;

		// Adding all the components
		while (scan.hasNext() && cnt < numberOfComponents) {
			components[cnt] = new Node(scan.next(), -1, cnt);
			cnt++;
		}

		int noOfDependencies = scan.nextInt();

		cnt = 0;

		// Adding all the components
		while (scan.hasNext() && cnt < noOfDependencies) {
			String parentNode = scan.next().trim();
			String childNode = scan.next().trim();

			int parentNodeIndex = getNodeIndex(components, parentNode);
			int childNodeIndex = getNodeIndex(components, childNode);

			if (parentNodeIndex > -1 && childNodeIndex > -1)
				adjMat[parentNodeIndex][childNodeIndex] = 1;
			else
				System.out.println("Invalid component found.");

			cnt++;
		}

		scan.close();

		// Running DFS to update the finish times of the components
		for (Node i : components) {
			if (i.color == NodeColor.White) {
				recursiveDFS(components, i, adjMat, false, null);
			}
		}

		// Sort components on the basis of decreasing finishing time as generated through DFS
		Arrays.sort(components);

		time = 0;

		// Resetting the color, parent node and noOfChildNodes for the components for the second DFS
		for (Node i : components) {
			i.color = NodeColor.White;
			i.parentNodeIndex = -1;
			i.noOfChildNodes = 0;
		}

		ArrayList<ConnectedComponents> finalComponents = new ArrayList<ConnectedComponents>();
		ArrayList<Node> connectedComponents = new ArrayList<Node>();

		for (Node i : components) {
			if (connectedComponents != null) {
				connectedComponents.clear();

				if (i.color == NodeColor.White)
					recursiveDFS(components, i, adjMat, true, connectedComponents);

				if (connectedComponents.size() > 0) {
					Collections.sort(connectedComponents, Node.ComparatorNodeIndex);
					
					// Adding the list of connected components to the final list
					finalComponents.add(new ConnectedComponents(connectedComponents.get(0).nodeIndex, connectedComponents));					
				}
			}
		}
		
		
		
		// Sorting the 'finalComponents' arraylist on the basis of Index
		Collections.sort(finalComponents, ConnectedComponents.ComparatorForComponents);
		
		// Printing the final list
		for(ConnectedComponents comp: finalComponents){
			
			for (int c = 0; c < comp.connectedComponents.size(); c++) {
				if (c < comp.connectedComponents.size() - 1)
					System.out.print(comp.connectedComponents.get(c).toString() + " ");
				else
					System.out.print(comp.connectedComponents.get(c).toString());
			}
			
			System.out.println("");
		}
		
	}

	// Method to recursively run a depth first search
	public static void recursiveDFS(Node[] components, Node node, int[][] adjacencyMatrix, boolean oppositeEdges,
			ArrayList<Node> connectedComponents) {
		// Setting the node as visited = true
		node.color = NodeColor.Gray;
		node.discoveryTime = ++time;

		// Getting the child nodes of index node
		ArrayList<Integer> lstChildNodes = GetChildNodes(node.nodeIndex, components, adjacencyMatrix, oppositeEdges);

		if (lstChildNodes != null && lstChildNodes.size() > 0) {
			for (int i = 0; i < lstChildNodes.size(); i++) {
				if (components[getNodeFromNodeIndex(components, lstChildNodes.get(i))].color == NodeColor.White) {
					components[getNodeFromNodeIndex(components, lstChildNodes.get(i))].parentNodeIndex = node.nodeIndex;
					node.noOfChildNodes = node.noOfChildNodes + 1;
					recursiveDFS(components, components[getNodeFromNodeIndex(components, lstChildNodes.get(i))],
							adjacencyMatrix, oppositeEdges, connectedComponents);
				}
			}
		}

		node.color = NodeColor.Black;
		node.finishingTime = ++time;

		if (oppositeEdges && (node.parentNodeIndex > -1 || node.noOfChildNodes > 0) && connectedComponents != null)
			connectedComponents.add(node);
	}

	// Method to get the child nodes of a given node
	public static ArrayList<Integer> GetChildNodes(int index, Node[] components, int[][] adjMatrix,
			boolean isOpposite) {
		ArrayList<Integer> nodes = new ArrayList<Integer>();

		for (int i = 0; i < components.length; i++) {

			if (!isOpposite) {
				if (adjMatrix[index][i] == 1) {
					nodes.add(i);
				}
			} else {
				if (adjMatrix[i][index] == 1) {
					nodes.add(i);
				}
			}
		}

		return nodes;
	}

	// Method to get the index of the node
	public static int getNodeIndex(Node[] components, String name) {

		for (int i = 0; i < components.length; i++)
			if (components[i].name.compareToIgnoreCase(name) == 0)
				return components[i].nodeIndex;

		return -1;
	}

	// Method to get a node from the node index
	public static int getNodeFromNodeIndex(Node[] components, int index) {

		for (int i = 0; i < components.length; i++)
			if (components[i].nodeIndex == index)
				return i;

		return -1;

	}

}

// Class to hold a final list of connected components found
class ConnectedComponents{
	int indexOfFirstNode;
	
	ArrayList<Node> connectedComponents;
	
	public ConnectedComponents(int index, ArrayList<Node> components){
		this.indexOfFirstNode = index;		
		this.connectedComponents = new ArrayList<Node>();
		
		for(Node node: components){
			this.connectedComponents.add(new Node(node.name, node.finishingTime, node.nodeIndex));
		}
		
	}
	
	public static Comparator<ConnectedComponents> ComparatorForComponents = new Comparator<ConnectedComponents>() {
		public int compare(ConnectedComponents c1, ConnectedComponents c2) {
			return c1.indexOfFirstNode - c2.indexOfFirstNode;
		}
	};
}


// Class for node of the graph
class Node implements Comparable<Node> {
	public String name;
	public NodeColor color;
	public Integer discoveryTime;
	public Integer finishingTime;
	public Integer nodeIndex;

	public int parentNodeIndex;
	public int noOfChildNodes;

	public Node(String name, int finishingTime, int nodeIndex) {
		this.name = name;
		this.color = NodeColor.White;
		this.finishingTime = finishingTime;
		this.discoveryTime = -1;
		this.nodeIndex = nodeIndex;
	}

	@Override
	public int compareTo(Node o) {
		return (o.finishingTime).compareTo(this.finishingTime);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static Comparator<Node> ComparatorNodeIndex = new Comparator<Node>() {
		public int compare(Node n1, Node n2) {
			return n1.nodeIndex - n2.nodeIndex;
		}
	};

}

enum NodeColor {
	White, Gray, Black
}
