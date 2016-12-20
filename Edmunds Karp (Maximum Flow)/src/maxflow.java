import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class maxflow {

	public static void main(String[] args) throws FileNotFoundException {

		File file = new File("Input.txt");

		Scanner scan = new Scanner(file);

		int n = scan.nextInt();
		int m = scan.nextInt();

		Node[] nodes = new Node[n];
		int[][] adjMatrix = new int[n][n];
		int[][] initialPaths = new int[m][2];

		for (int i = 0; i < m; i++) {
			int row = scan.nextInt();
			int col = scan.nextInt();

			nodes[row] = new Node(row);
			nodes[col] = new Node(col);

			adjMatrix[row][col] = scan.nextInt();

			// putting the sequence of nodes so as used to print the output
			initialPaths[i][0] = row;
			initialPaths[i][1] = col;
		}

		scan.close();
		
/*		for (int i = 0; i < n; i++) {

			for (int j = 0; j < n; j++) {
				System.out.print(adjMatrix[i][j] + "\t");
			}

			System.out.println();
		}

		System.out.println();
		System.out.println();*/

		int maxFlowPossible = MaxFlow(nodes, adjMatrix);

		System.out.println(maxFlowPossible);

		// Print all the edge weights
		printAllEdgeWeights(adjMatrix, initialPaths, m);
	}

	/*
	 * Method to maximize the network flow as per Edmund Karp algorithm
	 */
	public static int MaxFlow(Node[] nodes, int[][] adjMatrix) {

		int maxFlow = 0;

		int criticalPathWeight = 0;

		boolean residualPathAvailable = BFS(nodes, adjMatrix);

		// Maximize the flow till a residual path is available
		while (residualPathAvailable) {

			criticalPathWeight = calculateCriticalPathWeight(nodes, adjMatrix);

			maximizeFlow(nodes, adjMatrix, criticalPathWeight);

			residualPathAvailable = BFS(nodes, adjMatrix);
		}

		// Calculated the maximum flow possible
		maxFlow = calculateTheMaximumFlow(adjMatrix);

		return maxFlow;
	}

	/*
	 * Method to run a BFS search for finding the residual path
	 */
	public static boolean BFS(Node[] nodes, int[][] adjMatrix) {

		// Setting the color of all the nodes to white and parent node index to
		// -1
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].color = NodeColor.White;
			nodes[i].parentNodeIndex = -1;
		}

		// Setting the node color of the source vertex to gray
		nodes[0].color = NodeColor.Gray;

		Queue<Node> queueForBFS = new LinkedList<Node>();

		queueForBFS.add(nodes[0]);

		boolean sinkReached = false;

		while (!queueForBFS.isEmpty() && sinkReached == false) {
			// Dequeue the node
			Node source = queueForBFS.remove();

			// Get the neighboring nodes
			ArrayList<Integer> neighbouringNodes = getAdjacentNodes(adjMatrix, source.nodeIndex);

			for (int i = 0; i < neighbouringNodes.size(); i++) {
				if (nodes[neighbouringNodes.get(i)].color == NodeColor.White) {
					nodes[neighbouringNodes.get(i)].color = NodeColor.Gray;

					// Setting up the parent node index of the adjacent node
					nodes[neighbouringNodes.get(i)].parentNodeIndex = source.nodeIndex;

					// checking if we reached the sink
					if (nodes[neighbouringNodes.get(i)].nodeIndex == 1) {
						sinkReached = true;
						break;
					}

					queueForBFS.add(nodes[neighbouringNodes.get(i)]);
				}
			}
		}

		return sinkReached;
	}

	/*
	 * Traversing the BFS tree to calculate the critical path weight
	 */
	public static int calculateCriticalPathWeight(Node[] nodes, int[][] adjMatrix) {

		// Starting from sink adjust the weight of all the edges to source
		Node node = nodes[1];

		int criticalPathWeight = Integer.MAX_VALUE;

		while (node.nodeIndex != 0) {

			// adjusting the weight of the forward path
			if (criticalPathWeight > adjMatrix[node.parentNodeIndex][node.nodeIndex])
				criticalPathWeight = adjMatrix[node.parentNodeIndex][node.nodeIndex];

			node = nodes[node.parentNodeIndex];
		}

		return criticalPathWeight;
	}

	/*
	 * Traversing the BFS tree to adjust the path weights
	 */
	public static void maximizeFlow(Node[] nodes, int[][] adjMatrix, int criticalPathWeight) {

		// Starting from sink adjust the weight of all the edges to source
		Node node = nodes[1];

		while (node.nodeIndex != 0) {

			// adjusting the weight of the forward path
			adjMatrix[node.nodeIndex][node.parentNodeIndex] = adjMatrix[node.nodeIndex][node.parentNodeIndex]
					+ criticalPathWeight;

			// adjusting the weight of the backward path
			adjMatrix[node.parentNodeIndex][node.nodeIndex] = adjMatrix[node.parentNodeIndex][node.nodeIndex]
					- criticalPathWeight;

			node = nodes[node.parentNodeIndex];
		}
	}

	/*
	 * Method to calculate the maximum flow associated with the graph
	 */
	public static int calculateTheMaximumFlow(int[][] adjMatrix) {

		int maxFlow = 0;

		for (int i = 0; i < adjMatrix.length; i++) {
			maxFlow += adjMatrix[1][i];
		}

		// Print the maximum flow
		return maxFlow;
	}

	/*
	 * Method to print the final edge weights
	 */
	public static void printAllEdgeWeights(int[][] adjMatrix, int[][] initialPath, int noOfPaths) {
		// Print all the edge weights
		for (int i = 0; i < noOfPaths; i++) {
			int row = initialPath[i][0];
			int col = initialPath[i][1];
			
			System.out.println(row + " " + col + " " + adjMatrix[col][row]);
		}
	}

	/*
	 * Method to get the adjacent nodes for a given source vertex index
	 */
	public static ArrayList<Integer> getAdjacentNodes(int[][] adjMatrix, int index) {
		ArrayList<Integer> listOfNodes = new ArrayList<Integer>();

		for (int i = 0; i < adjMatrix.length; i++) {
			if (adjMatrix[index][i] > 0)
				listOfNodes.add(i);
		}

		return listOfNodes;
	}
}

/*
 * Class for a node in graph to run BFS search
 */
class Node {
	public int nodeIndex;
	public NodeColor color;

	public int parentNodeIndex;

	public Node(int nodeIndex) {
		this.nodeIndex = nodeIndex;

		this.parentNodeIndex = -1;
	}
}

enum NodeColor {
	White, Gray, Black
}