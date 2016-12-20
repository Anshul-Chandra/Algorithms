
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.lang.StringBuilder;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class GreedySearch {
	
	public static void greedySearch(int sourceCityIndex, int destinationCityIndex, City[] cities, int[][] adjMat){
		
		int noOfNodesExpanded = 0;
		
		System.out.println("\n============================================");
		
		System.out.println("Greedy search algorithm");

		System.out.println("============================================\n");
		
		
		// Stringbuilder for saving the nodes expanded
		StringBuilder sb = new StringBuilder();
		
		// Used for sorting the list
		Comparator<Node> objSort = new SortingPriorityQueue();
		
		// Adding the source node to queue
		Node source = new Node(sourceCityIndex, SearchUSA.getCityName(cities, sourceCityIndex));
		
		double hueristicCostForSource = cities[sourceCityIndex].getHeuristicValue(cities[destinationCityIndex]);
		source.setPathCost(hueristicCostForSource);
		source.setTotalPathCost(hueristicCostForSource);
		
		// Creating a priority queue for the nodes
		PriorityQueue<Node> cityQueue = new PriorityQueue<Node>(11, objSort);
		
		// Adding the source node to the path
		cityQueue.offer(source);
		
		while(!cityQueue.isEmpty()){
			try{
				// remove the first element of the queue and expand further
				Node expandedNode = cityQueue.remove();	
				
				sb.append(cities[expandedNode.getCityIndex()].getCityName() + ", ");
				
				noOfNodesExpanded++;
				
				// setting up the visited flag of the city as true
				cities[expandedNode.getCityIndex()].setIsVisited(true);
				
				// Check if the node matched the goal
				if(expandedNode.getCityIndex() == destinationCityIndex){
					// Print the resulting path
					System.out.println("Final Path = " + expandedNode.getPath());
					
					expandedNode.setNoOfNodesInThePath(expandedNode.getNoOfNodesInThePath() + 1);
					System.out.println("\nNo. of nodes in the path (including source and destination) = " + expandedNode.getNoOfNodesInThePath());
					
					String pathExpansion = sb.toString();
					pathExpansion = pathExpansion.substring(0, pathExpansion.lastIndexOf(","));
					
					System.out.println("\nSequence of nodes expanded = " + pathExpansion);
					
					System.out.println("\nNo. of nodes expanded = " + noOfNodesExpanded);
					
					// Total distance from A to B
					NumberFormat frmt = new DecimalFormat("##.##");
					System.out.println("\nTotal distance covered = " + frmt.format(expandedNode.getPathCost()));
					
					break;
				}
					
				// Find the neighbors of the node through adjacency matrix
				Map<Integer, Integer> adjNodes = SearchUSA.getAdjacentNode(expandedNode.getCityIndex(), adjMat, true, cities);
				
				for(int i = 0; i < adjMat[0].length; i++){
					if(adjNodes.containsKey(i)){
						Node adjCity = new Node(i, expandedNode.getPath() + " --> " + SearchUSA.getCityName(cities, i));
					
						adjCity.setNoOfNodesInThePath(expandedNode.getNoOfNodesInThePath() + 1);
						
						//double hueristicCost = heuristic.containsKey(i) ? heuristic.get(i) : 0;
						
						double heuristicCost = cities[i].getHeuristicValue(cities[destinationCityIndex]);
						double pathCost = adjNodes.get(i) + expandedNode.getPathCost();
					
						// Calculate the cost of the node
						adjCity.setPathCost(pathCost);
						// Path cost is zero in this case as Greedy search algorithm does not
						// takes into account the edge lengths, hence total cost = heuristic cost
						adjCity.setTotalPathCost(heuristicCost);
						
						// Add the node to the queue
						cityQueue.offer(adjCity);
					}
				}
				
			} catch (Exception ex){
				
			}
			
		}
	}

}