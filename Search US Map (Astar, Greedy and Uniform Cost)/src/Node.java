
public class Node {
	// Holds the index of the last city in the path 
	private int cityIndex;
	// Holds the path used to reach the node (eg. S --> B or S --> A --> B)
	private String path;
	
	// Holds the value of the path cost, i.e. edge length between current and source node
	private double pathCost;
	
	// Holds the total value of the path cost including path cost as well as heuristic path cost
	private double totalPathCost;
	
	private int noOfNodesInThePath;
	
	public Node(int cityIndex, String path){
		this.cityIndex = cityIndex;
		this.path = path;
	}
	
	// Get-Set properties for the attributes
	public int getCityIndex(){
		return cityIndex;
	}
	
	public void setPathCost(double value){
		this.pathCost = value;
	}
	
	public double getPathCost(){
		return this.pathCost;
	}
	
	
	public void setTotalPathCost(double value){
		this.totalPathCost = value;
	}
	
	public double getTotalPathCost(){
		return this.totalPathCost;
	}
	
	public void setNoOfNodesInThePath(int value){
		this.noOfNodesInThePath = value;
	}
	
	public int getNoOfNodesInThePath(){
		return this.noOfNodesInThePath;
	}
	
	public String getPath(){
		return this.path;
	}
	
}
