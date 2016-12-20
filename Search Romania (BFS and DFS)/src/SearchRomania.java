
/*
 * Name			: Anshul Chandra
 * Unity ID		: achand13
 * Student ID	: 200158929
 */

import java.util.*;

public class SearchRomania {

	public static void main(String[] args) {

		try{
			// Storing the details of the cities [start]
			Cities[] listOfCities = new Cities[20];
			
			addCities(listOfCities);
			// Storing the details of the cities [end]
			
			// Maintaining the adjacency-matrix [start]
			int[][] adjacencyMatrix = new int[20][20];
			
			createAdjacencyMatrix(adjacencyMatrix);
			// Maintaining the adjacency-matrix [end]
			
			
			if(args.length > 0){
				String searchType = args[0].trim().toLowerCase();
				int sourceCityIndex, destinationCityIndex;
				
				sourceCityIndex = -1;
				destinationCityIndex = -1;
				
				if(args.length > 1)
					sourceCityIndex = GetIndexForCity(listOfCities, args[1].trim().toLowerCase().replace(" ", "_"));
				else
					System.out.println("Please provide a source city to begin search.");	
				
				if(args.length > 2)
					destinationCityIndex = GetIndexForCity(listOfCities, args[2].trim().toLowerCase().replace(" ", "_"));
				else
					System.out.println("Please provide a target city to begin search.");
				
				
				if(searchType != null && sourceCityIndex > -1 && destinationCityIndex > -1){
					if(searchType.equals("bfs") )
						BFS(listOfCities, sourceCityIndex, destinationCityIndex, adjacencyMatrix); // Method call for BFS Search algorithm
					else if(searchType.equals("dfs"))
						DFS(listOfCities, sourceCityIndex, destinationCityIndex, adjacencyMatrix); // Method call for DFS search algorithm
				}
			}
			else
				System.out.println("Please provide a search type ('DFS' or 'BFS') to begin.");
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}		
	}
	
	/* Description	: Method to get the unvisited city connected to a given city
	 * Parameters	: index of the city for which unvisited cities are to be found
	 * Returns 		: index of the unvisited city; -1 in case no unvisited city is found
	 */
	private static int GetUnvisitedCities(int indexOfCity, Cities[] listOfCities, int[][] adjacencyMatrix){
		for(int i = 0; i < 20; i++){
			if(adjacencyMatrix[indexOfCity][i] == 1 && listOfCities[i].getIsVisited() == false)
				return i;
		}
		
		return -1;
	}

	/* Description	: Method to get the index number against a provided city name
	 * Parameters	: name of the city
	 * Returns 		: index of the city; -1 in case no city is found
	 */
	private static int GetIndexForCity(Cities[] listOfCities, String cityName){
		if(cityName != null) {
			for(int i = 0; i < 20; i++){
				if(listOfCities[i].getCityName().equals(cityName))
					return i;
			}
		}
		
		return -1;
	}
	
	/* Description	: Function to run a BFS search between source and destination cities provided
	 * Parameters	: index of the source city, index of destination city, list of all cities, adjacency matrix
	 * Returns 		: void
	 */
	private static void BFS(Cities[] listOfCities, int indexOfSourceCity, int indexOfDestinationCity, int[][] adjacencyMatrix){		
		
		int indexOfUnvisitedCity, topElement;
		int countOfCitiesVisited = 0;
		boolean destinationReached = false;
		Queue<Integer> queueForBFS = new LinkedList<Integer>();
		
		System.out.println("Sequence of cities visted through BFS search");
		
		// Add the source node to the queue
		queueForBFS.add(indexOfSourceCity);
		// Setting the node as visited = true
		listOfCities[indexOfSourceCity].setIsVisited(true);
		// incrementing the counter for cities visited
		countOfCitiesVisited++;
		// Print the element
		System.out.print(listOfCities[indexOfSourceCity].getCityName());
		
		while(!(queueForBFS.isEmpty()) && destinationReached == false){
			
			try {
				//remove the top element of queue
				topElement = queueForBFS.remove();
				// Get the adjacent unvisited city
				indexOfUnvisitedCity = GetUnvisitedCities(topElement, listOfCities, adjacencyMatrix);
				
				while(indexOfUnvisitedCity != -1){
					if(indexOfUnvisitedCity == indexOfDestinationCity){
						// We reached destination - set the visited flag to true
						listOfCities[indexOfUnvisitedCity].setIsVisited(true);
						// incrementing the counter for cities visited
						countOfCitiesVisited++;
						System.out.println(" --> " + listOfCities[indexOfUnvisitedCity].getCityName());
						destinationReached = true;
						break;
					}
					else {
						// Expanding the top element - Add the unvisited element in the queue
						queueForBFS.add(indexOfUnvisitedCity);
						// Mark the element as visited
						listOfCities[indexOfUnvisitedCity].setIsVisited(true);
						
						// incrementing the counter for cities visited
						countOfCitiesVisited++;
						
						// Print the name of the city
						System.out.print(" --> " + listOfCities[indexOfUnvisitedCity].getCityName());
						
						// Find the next unvisited node (if one exists)
						indexOfUnvisitedCity = GetUnvisitedCities(topElement, listOfCities, adjacencyMatrix);
					}
				}
			}
			catch(NoSuchElementException ex){
				// Display the top element of the queue and break 
				System.out.print(listOfCities[indexOfSourceCity].getCityName());
				break;
			}
			catch(Exception generalException){
				System.out.println(generalException.getMessage());
				break;
			}
		}
		
		System.out.println("");
		System.out.println("The count of cities visited (including source and destination) through BFS search: " + countOfCitiesVisited);
	}
	
	/* Description	: Function to run a DFS search between source and destination cities provided
	 * Parameters	: index of the source city, index of destination city, list of all cities, adjacency matrix
	 * Returns 		: void
	 */
	public static void DFS(Cities[] listOfCities, int indexOfSourceCity, int indexOfDestinationCity, int[][] adjacencyMatrix){
		
		Deque<Integer> stackForDFS = new ArrayDeque<Integer>();
		
		int indexOfUnvisitedCity, topElement;
		boolean destinationReached = false;
		
		int countOfCitiesVisited = 0;
		
		System.out.println("Sequence of cities visted through DFS search");
		
		// Add the source node to the queue
		stackForDFS.push(indexOfSourceCity);
		
		// Setting the node as visited = true
		listOfCities[indexOfSourceCity].setIsVisited(true);
		
		// incrementing the counter for cities visited
		countOfCitiesVisited++;
		
		// Print the element
		System.out.print(listOfCities[indexOfSourceCity].getCityName());
		
		topElement = indexOfSourceCity;
		
		while(!(stackForDFS.isEmpty()) && destinationReached == false){
			
			
			try {
				// remove the top element of the stack
				topElement = stackForDFS.element();
				
				// Getting the unvisited city attached with the current city
				indexOfUnvisitedCity = GetUnvisitedCities(topElement, listOfCities, adjacencyMatrix);
				
				if (indexOfUnvisitedCity != -1) {
					// case: found a connected city which has not been visited yet
					
					// check if the city is our destination city
					if(indexOfUnvisitedCity == indexOfDestinationCity){
						//Setting up the city as 'visited'
						listOfCities[indexOfUnvisitedCity].setIsVisited(true);
						
						// incrementing the counter for cities visited
						countOfCitiesVisited++;
						
						// print the name of the city
						System.out.println(" --> " + listOfCities[indexOfUnvisitedCity].getCityName());
						destinationReached = true;
					}
					else {
						// print the name of the city
						System.out.print(" --> " + listOfCities[indexOfUnvisitedCity].getCityName());
						
						// Set the visited flag of the city to true
						listOfCities[indexOfUnvisitedCity].setIsVisited(true);
						
						// incrementing the counter for cities visited
						countOfCitiesVisited++;
						
						// Add the city to the stack
						stackForDFS.push(indexOfUnvisitedCity);
					}
				}
				else {
					// case: no connected city found
					// pop the top element of the stack
					topElement = stackForDFS.remove();	
				}
			}
			catch (NoSuchElementException ex) {
				System.out.println("");
				System.out.println("Unfortunately an error has occured.");
				break;
			}
			catch (Exception ex) {
				System.out.println("Unfortunately an error has occured.");
				break;
			}
			
		}
		
		System.out.println("");
		System.out.println("The count of cities visited (including source and destination) through DFS search: " + countOfCitiesVisited);
	}
	
	
	/* Description	: Method to add city data to list of cities
	 * Parameters	: array of objects for class City
	 * Returns		: void
	 */
	private static void addCities(Cities[] listOfCities){
		listOfCities[0] = new Cities("arad");
		listOfCities[1] = new Cities("bucharest");
		listOfCities[2] = new Cities("craiova");
		listOfCities[3] = new Cities("dobreta");
		listOfCities[4] = new Cities("eforie");
		listOfCities[5] = new Cities("fagaras");
		listOfCities[6] = new Cities("giurgiu");
		listOfCities[7] = new Cities("hirsova");
		listOfCities[8] = new Cities("iasi");
		listOfCities[9] = new Cities("lugoj");
		listOfCities[10] = new Cities("mehadia");
		listOfCities[11] = new Cities("neamt");
		listOfCities[12] = new Cities("oradea");
		listOfCities[13] = new Cities("pitesti");
		listOfCities[14] = new Cities("rimnicu_vilcea");
		listOfCities[15] = new Cities("sibiu");
		listOfCities[16] = new Cities("timisoara");
		listOfCities[17] = new Cities("urziceni");
		listOfCities[18] = new Cities("vaslui");
		listOfCities[19] = new Cities("zerind");
	}
	
	/* Description	: Method to create adjacency matrix for cities
	 * Parameters	: 2-dimensional array for storing adjacency data
	 * Returns		: void
	 */
	private static void createAdjacencyMatrix(int[][] adjacencyMatrix){
		adjacencyMatrix[12][19]	= 1;
		adjacencyMatrix[19][0]	= 1;
		adjacencyMatrix[0][16] 	= 1;
		adjacencyMatrix[16][9] 	= 1;
		adjacencyMatrix[9][10] 	= 1;
		adjacencyMatrix[3][10] 	= 1;
		adjacencyMatrix[12][15] = 1;
		adjacencyMatrix[0][15] 	= 1;
		adjacencyMatrix[3][2] 	= 1;
		adjacencyMatrix[15][14] = 1;
		adjacencyMatrix[15][5] 	= 1;
		adjacencyMatrix[14][2] 	= 1;
		adjacencyMatrix[13][2] 	= 1;
		adjacencyMatrix[14][13]	= 1;
		adjacencyMatrix[1][13] 	= 1;
		adjacencyMatrix[1][5] 	= 1;
		adjacencyMatrix[1][6] 	= 1;
		adjacencyMatrix[1][17] 	= 1;
		adjacencyMatrix[18][17] = 1;
		adjacencyMatrix[7][17] 	= 1;
		adjacencyMatrix[7][4] 	= 1;
		adjacencyMatrix[18][8] 	= 1;
		adjacencyMatrix[11][8] 	= 1;
		
		adjacencyMatrix[19][12] = 1;
		adjacencyMatrix[0][19] 	= 1;
		adjacencyMatrix[16][0] 	= 1;
		adjacencyMatrix[9][16] 	= 1;
		adjacencyMatrix[10][9] 	= 1;
		adjacencyMatrix[10][3] 	= 1;
		adjacencyMatrix[15][12] = 1;
		adjacencyMatrix[15][0] 	= 1;
		adjacencyMatrix[2][3] 	= 1;
		adjacencyMatrix[14][15] = 1;
		adjacencyMatrix[5][15] 	= 1;
		adjacencyMatrix[2][14] 	= 1;
		adjacencyMatrix[2][13] 	= 1;
		adjacencyMatrix[13][14]	= 1;
		adjacencyMatrix[13][1] 	= 1;
		adjacencyMatrix[5][1] 	= 1;
		adjacencyMatrix[6][1] 	= 1;
		adjacencyMatrix[17][1] 	= 1;
		adjacencyMatrix[17][18] = 1;
		adjacencyMatrix[17][7] 	= 1;
		adjacencyMatrix[4][7] 	= 1;
		adjacencyMatrix[8][18] 	= 1;
		adjacencyMatrix[8][11] 	= 1;
	}
	
}


// Class defined or storing details of a city
class Cities {
	private String cityName;
	private boolean isVisited;
	
	
	public Cities(String cityName){
		this.cityName = cityName;
		this.isVisited = false;
	}
	
	// Get-Set properties for 'cityName' [start]
	public String getCityName(){
		return this.cityName;
	}
		
	public void setCityName(String value){
		this.cityName = value;
	}
	// Get-Set properties for 'cityName' [end]
	
	// Get-Set properties for 'isVisited' [start]
	public boolean getIsVisited(){
		return this.isVisited;
	}
		
	public void setIsVisited(boolean value){
		this.isVisited = value;
	}
	// Get-Set properties for 'isVisited' [end]	
}