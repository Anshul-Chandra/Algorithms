
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.lang.StringBuilder;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class AStarSearch {

	public static void aStarSearch(int sourceCityIndex, int destinationCityIndex, City[] cities, int[][] adjMat) {

		int noOfNodesExpanded = 0;

		System.out.println("\n============================================");
		
		System.out.println("A* search algorithm");

		System.out.println("============================================\n");
		
		// Stringbuilder for saving the nodes expanded
		StringBuilder sb = new StringBuilder();

		// Used for sorting the list
		Comparator<Node> objSort = new SortingPriorityQueue();

		// Adding the source node to queue
		Node source = new Node(sourceCityIndex, SearchUSA.getCityName(cities, sourceCityIndex));
		double heuristicCostForSource = cities[sourceCityIndex].getHeuristicValue(cities[destinationCityIndex]);
		source.setPathCost(0);
		source.setTotalPathCost(heuristicCostForSource);
		source.setNoOfNodesInThePath(1);

		// Creating a priority queue for the nodes
		PriorityQueue<Node> cityQueue = new PriorityQueue<Node>(11, objSort);

		// Adding the source node to the path
		cityQueue.offer(source);

		while (!cityQueue.isEmpty()) {
			try {
				// remove the first element of the queue and expand further
				Node expandedNode = cityQueue.remove();

				// sb.append(SearchUSA.getCityName(cities,
				// expandedNode.getCityIndex()));
				//sb.append(expandedNode.getPath() + ", ");
				sb.append(cities[expandedNode.getCityIndex()].getCityName() + ", ");

				noOfNodesExpanded++;

				cities[expandedNode.getCityIndex()].setIsVisited(true);

				// Check if the node matched the goal
				if (expandedNode.getCityIndex() == destinationCityIndex) {
					// Print the resulting path
					System.out.println("Final path = " + expandedNode.getPath());
					
					System.out.println("\nNo. of nodes in the path (including source and destination) = " + expandedNode.getNoOfNodesInThePath());

					String pathExpansion = sb.toString();
					pathExpansion = pathExpansion.substring(0, pathExpansion.lastIndexOf(","));

					System.out.println("\nSequence of nodes expanded = " + pathExpansion);

					System.out.println("No. of nodes expanded = " + noOfNodesExpanded);
					
					// Total distance from A to B
					NumberFormat frmt = new DecimalFormat("##.##");
					System.out.println("\nTotal distance covered = " + frmt.format(expandedNode.getPathCost()));
					break;
				}

				// Find the neighbors of the node through adjacency matrix
				Map<Integer, Integer> adjNodes = SearchUSA.getAdjacentNode(expandedNode.getCityIndex(), adjMat, true, cities);

				for (int i = 0; i < adjMat[0].length; i++) {
					if (adjNodes.containsKey(i)) {
						Node adjCity = new Node(i, expandedNode.getPath() + " --> " + SearchUSA.getCityName(cities, i));

						adjCity.setNoOfNodesInThePath(expandedNode.getNoOfNodesInThePath() + 1);

						double hueristicCost = cities[i].getHeuristicValue(cities[destinationCityIndex]);
						double pathCost = adjNodes.get(i) + expandedNode.getPathCost();

						// Calculate the cost of the node
						adjCity.setPathCost(pathCost);
						adjCity.setTotalPathCost(hueristicCost + pathCost);

						// Add the node to the queue
						cityQueue.offer(adjCity);
					}
				}

			} catch (Exception ex) {
				System.out.println("An error has occured. Unable to proceed with the search. . .");
			}

		}
	}

}

final class SortingPriorityQueue implements Comparator<Node> {

	public SortingPriorityQueue() {
	}

	@Override
	public int compare(Node obj1, Node obj2) {
		if (obj1.getTotalPathCost() < obj2.getTotalPathCost())
			return -1;
		if (obj1.getTotalPathCost() > obj2.getTotalPathCost())
			return 1;

		return 0;
	}
}