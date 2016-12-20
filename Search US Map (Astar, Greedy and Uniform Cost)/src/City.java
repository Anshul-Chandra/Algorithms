import java.lang.Math;


// Class to hold the details of the cities of USA used for searching
public class City {
	
	final double pi = 3.14159; 
	
	private String cityName;
	private boolean isVisisted;
	
	private double latitude;
	private double longitude;
	
	
	public City(String cityName){
		this.cityName = cityName;
		this.isVisisted = false;
	}
	
	public City(String cityName, double latitude, double longitude){
		this.cityName = cityName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isVisisted = false;		
	}
	
	// Get-Set Properties for the attributes
	public String getCityName(){
		return this.cityName;
	}
	
	public boolean getIsVisited(){
		return this.isVisisted;
	}
	
	public void setIsVisited(boolean value){
		this.isVisisted = value;
	}
	
	public double getHeuristicValue(City cityDestination){
		double heuristicValue = 0;
		
		heuristicValue = Math.sqrt(Math.pow(69.5 * (this.latitude - cityDestination.latitude), 2) + Math.pow(69.5 * Math.cos((this.latitude + cityDestination.latitude)/360 * pi) * (this.longitude - cityDestination.longitude), 2));
		
		return heuristicValue;
	}
	
}
