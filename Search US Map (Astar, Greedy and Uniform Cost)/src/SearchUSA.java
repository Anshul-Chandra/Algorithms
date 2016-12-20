import java.util.HashMap;
import java.util.Map;

public class SearchUSA {

	public static void main(String[] args) {

		try {
			City[] listOfCities = new City[112];

			int[][] adjacencyMatrix = new int[112][112];

			addCities(listOfCities);

			createAdjacencyMatrix(adjacencyMatrix);

			if (args.length > 0) {
				String searchAlgo = args[0].trim().toLowerCase();
				int sourceCityIndex, destinationCityIndex;

				sourceCityIndex = -1;
				destinationCityIndex = -1;

				if (args.length > 1)
					sourceCityIndex = getCityIndex(listOfCities, args[1].trim().toLowerCase().replace(" ", "_"));
				else
					System.out.println("Please provide a source city to begin search.");

				if (args.length > 2)
					destinationCityIndex = getCityIndex(listOfCities, args[2].trim().toLowerCase().replace(" ", "_"));
				else
					System.out.println("Please provide a target city to begin search.");

				if (searchAlgo != null && sourceCityIndex > -1 && destinationCityIndex > -1) {
					if (searchAlgo.equals("astar"))
						// case: A* search
						AStarSearch.aStarSearch(sourceCityIndex, destinationCityIndex, listOfCities, adjacencyMatrix);
					else if (searchAlgo.equals("greedy"))
						// case: Greedy search
						GreedySearch.greedySearch(sourceCityIndex, destinationCityIndex, listOfCities, adjacencyMatrix);
					else if (searchAlgo.equals("uniform"))
						// case: Uniform Cost
						UniformCostSearch.uniformCostSearch(sourceCityIndex, destinationCityIndex, listOfCities,
								adjacencyMatrix);
					else
						System.out.println("Invalid search algorithm (Valid search algorithms - 'astar', 'greedy', and 'uniform')");
				}
				else{
					if(sourceCityIndex == -1)
						System.out.println("Unable to find the source city - " + args[1].trim());
					
					if(destinationCityIndex == -1)
						System.out.println("Unable to find the destination city - " + args[2].trim());
				}
			} else
				System.out.println("Please provide a search type ('astar', 'greedy', or 'uniform') to begin.");
		} catch (Exception ex) {
			System.out.println("An error has occured. Unable to proceed with the search. . .");
		}

	}

	// Method to add the cities to city array
	private static void addCities(City[] listOfCities) {
		listOfCities[0] = new City("albanyGA", 31.58, 84.17);
		listOfCities[1] = new City("albanyNY", 42.66, 73.78);
		listOfCities[2] = new City("albuquerque", 35.11, 106.61);
		listOfCities[3] = new City("atlanta", 33.76, 84.4);
		listOfCities[4] = new City("augusta", 33.43, 82.02);
		listOfCities[5] = new City("austin", 30.3, 97.75);
		listOfCities[6] = new City("bakersfield", 35.36, 119.03);
		listOfCities[7] = new City("baltimore", 39.31, 76.62);
		listOfCities[8] = new City("batonRouge", 30.46, 91.14);
		listOfCities[9] = new City("beaumont", 30.08, 94.13);
		listOfCities[10] = new City("boise", 43.61, 116.24);
		listOfCities[11] = new City("boston", 42.32, 71.09);
		listOfCities[12] = new City("buffalo", 42.9, 78.85);
		listOfCities[13] = new City("calgary", 51, 114);
		listOfCities[14] = new City("charlotte", 35.21, 80.83);
		listOfCities[15] = new City("chattanooga", 35.05, 85.27);
		listOfCities[16] = new City("chicago", 41.84, 87.68);
		listOfCities[17] = new City("cincinnati", 39.14, 84.5);
		listOfCities[18] = new City("cleveland", 41.48, 81.67);
		listOfCities[19] = new City("coloradoSprings", 38.86, 104.79);
		listOfCities[20] = new City("columbus", 39.99, 82.99);
		listOfCities[21] = new City("dallas", 32.8, 96.79);
		listOfCities[22] = new City("dayton", 39.76, 84.2);
		listOfCities[23] = new City("daytonaBeach", 29.21, 81.04);
		listOfCities[24] = new City("denver", 39.73, 104.97);
		listOfCities[25] = new City("desMoines", 41.59, 93.62);
		listOfCities[26] = new City("elPaso", 31.79, 106.42);
		listOfCities[27] = new City("eugene", 44.06, 123.11);
		listOfCities[28] = new City("europe", 48.87, -2.33);
		listOfCities[29] = new City("fresno", 36.78, 119.79);
		listOfCities[30] = new City("ftWorth", 32.74, 97.33);
		listOfCities[31] = new City("grandJunction", 39.08, 108.56);
		listOfCities[32] = new City("greenBay", 44.51, 88.02);
		listOfCities[33] = new City("greensboro", 36.08, 79.82);
		listOfCities[34] = new City("houston", 29.76, 95.38);
		listOfCities[35] = new City("indianapolis", 39.79, 86.15);
		listOfCities[36] = new City("jacksonville", 30.32, 81.66);
		listOfCities[37] = new City("japan", 35.68, 220.23);
		listOfCities[38] = new City("kansasCity", 39.08, 94.56);
		listOfCities[39] = new City("keyWest", 24.56, 81.78);
		listOfCities[40] = new City("lafayette", 30.21, 92.03);
		listOfCities[41] = new City("lakeCity", 30.19, 82.64);
		listOfCities[42] = new City("laredo", 27.52, 99.49);
		listOfCities[43] = new City("lasVegas", 36.19, 115.22);
		listOfCities[44] = new City("lincoln", 40.81, 96.68);
		listOfCities[45] = new City("littleRock", 34.74, 92.33);
		listOfCities[46] = new City("losAngeles", 34.03, 118.17);
		listOfCities[47] = new City("macon", 32.83, 83.65);
		listOfCities[48] = new City("medford", 42.33, 122.86);
		listOfCities[49] = new City("memphis", 35.12, 89.97);
		listOfCities[50] = new City("mexia", 31.68, 96.48);
		listOfCities[51] = new City("mexico", 19.4, 99.12);
		listOfCities[52] = new City("miami", 25.79, 80.22);
		listOfCities[53] = new City("midland", 43.62, 84.23);
		listOfCities[54] = new City("milwaukee", 43.05, 87.96);
		listOfCities[55] = new City("minneapolis", 44.96, 93.27);
		listOfCities[56] = new City("modesto", 37.66, 120.99);
		listOfCities[57] = new City("montreal", 45.5, 73.67);
		listOfCities[58] = new City("nashville", 36.15, 86.76);
		listOfCities[59] = new City("newHaven", 41.31, 72.92);
		listOfCities[60] = new City("newOrleans", 29.97, 90.06);
		listOfCities[61] = new City("newYork", 40.7, 73.92);
		listOfCities[62] = new City("norfolk", 36.89, 76.26);
		listOfCities[63] = new City("oakland", 37.8, 122.23);
		listOfCities[64] = new City("oklahomaCity", 35.48, 97.53);
		listOfCities[65] = new City("omaha", 41.26, 96.01);
		listOfCities[66] = new City("orlando", 28.53, 81.38);
		listOfCities[67] = new City("ottawa", 45.42, 75.69);
		listOfCities[68] = new City("pensacola", 30.44, 87.21);
		listOfCities[69] = new City("philadelphia", 40.72, 76.12);
		listOfCities[70] = new City("phoenix", 33.53, 112.08);
		listOfCities[71] = new City("pittsburgh", 40.4, 79.84);
		listOfCities[72] = new City("pointReyes", 38.07, 122.81);
		listOfCities[73] = new City("portland", 45.52, 122.64);
		listOfCities[74] = new City("providence", 41.8, 71.36);
		listOfCities[75] = new City("provo", 40.24, 111.66);
		listOfCities[76] = new City("raleigh", 35.82, 78.64);
		listOfCities[77] = new City("redding", 40.58, 122.37);
		listOfCities[78] = new City("reno", 39.53, 119.82);
		listOfCities[79] = new City("richmond", 37.54, 77.46);
		listOfCities[80] = new City("rochester", 43.17, 77.61);
		listOfCities[81] = new City("sacramento", 38.56, 121.47);
		listOfCities[82] = new City("salem", 44.93, 123.03);
		listOfCities[83] = new City("salinas", 36.68, 121.64);
		listOfCities[84] = new City("saltLakeCity", 40.75, 111.89);
		listOfCities[85] = new City("sanAntonio", 29.45, 98.51);
		listOfCities[86] = new City("sanDiego", 32.78, 117.15);
		listOfCities[87] = new City("sanFrancisco", 37.76, 122.44);
		listOfCities[88] = new City("sanJose", 37.3, 121.87);
		listOfCities[89] = new City("sanLuisObispo", 35.27, 120.66);
		listOfCities[90] = new City("santaFe", 35.67, 105.96);
		listOfCities[91] = new City("saultSteMarie", 46.49, 84.35);
		listOfCities[92] = new City("savannah", 32.05, 81.1);
		listOfCities[93] = new City("seattle", 47.63, 122.33);
		listOfCities[94] = new City("stamford", 41.07, 73.54);
		listOfCities[95] = new City("stLouis", 38.63, 90.24);
		listOfCities[96] = new City("stockton", 37.98, 121.3);
		listOfCities[97] = new City("tallahassee", 30.45, 84.27);
		listOfCities[98] = new City("tampa", 27.97, 82.46);
		listOfCities[99] = new City("thunderBay", 48.38, 89.25);
		listOfCities[100] = new City("toledo", 41.67, 83.58);
		listOfCities[101] = new City("toronto", 43.65, 79.38);
		listOfCities[102] = new City("tucson", 32.21, 110.92);
		listOfCities[103] = new City("tulsa", 36.13, 95.94);
		listOfCities[104] = new City("uk1", 51.3, 0);
		listOfCities[105] = new City("uk2", 51.3, 0);
		listOfCities[106] = new City("vancouver", 49.25, 123.1);
		listOfCities[107] = new City("washington", 38.91, 77.01);
		listOfCities[108] = new City("westPalmBeach", 26.71, 80.05);
		listOfCities[109] = new City("wichita", 37.69, 97.34);
		listOfCities[110] = new City("winnipeg", 49.9, 97.13);
		listOfCities[111] = new City("yuma", 32.69, 114.62);

	}

	// Method to create the adjacency list
	private static void createAdjacencyMatrix(int[][] adjMat) {
		adjMat[0][47] = 106;
		adjMat[0][97] = 120;
		adjMat[1][11] = 166;
		adjMat[1][57] = 226;
		adjMat[1][80] = 148;
		adjMat[2][26] = 267;
		adjMat[2][90] = 61;
		adjMat[3][15] = 117;
		adjMat[3][47] = 82;
		adjMat[4][14] = 161;
		adjMat[4][92] = 131;
		adjMat[5][34] = 186;
		adjMat[5][85] = 79;
		adjMat[6][29] = 107;
		adjMat[6][46] = 112;
		adjMat[7][69] = 102;
		adjMat[7][107] = 45;
		adjMat[8][40] = 50;
		adjMat[8][60] = 80;
		adjMat[9][34] = 69;
		adjMat[9][40] = 122;
		adjMat[10][73] = 428;
		adjMat[10][84] = 349;
		adjMat[11][74] = 51;
		adjMat[12][18] = 191;
		adjMat[12][80] = 164;
		adjMat[12][80] = 64;
		adjMat[12][101] = 105;
		adjMat[12][101] = 105;
		adjMat[13][106] = 605;
		adjMat[13][110] = 829;
		adjMat[14][33] = 91;
		adjMat[15][58] = 129;
		adjMat[16][53] = 279;
		adjMat[16][54] = 90;
		adjMat[17][22] = 56;
		adjMat[17][35] = 110;
		adjMat[18][20] = 142;
		adjMat[18][71] = 157;
		adjMat[19][24] = 70;
		adjMat[19][90] = 316;
		adjMat[20][22] = 72;
		adjMat[21][24] = 792;
		adjMat[21][50] = 83;
		adjMat[23][36] = 92;
		adjMat[23][66] = 54;
		adjMat[24][31] = 246;
		adjMat[24][109] = 523;
		adjMat[25][55] = 246;
		adjMat[25][65] = 135;
		adjMat[26][85] = 580;
		adjMat[26][102] = 320;
		adjMat[27][48] = 165;
		adjMat[27][82] = 63;
		adjMat[28][69] = 3939;
		adjMat[29][56] = 109;
		adjMat[30][64] = 209;
		adjMat[31][75] = 220;
		adjMat[32][54] = 117;
		adjMat[32][55] = 304;
		adjMat[33][76] = 74;
		adjMat[34][50] = 165;
		adjMat[35][95] = 246;
		adjMat[36][41] = 113;
		adjMat[36][92] = 140;
		adjMat[37][72] = 5131;
		adjMat[37][89] = 5451;
		adjMat[38][95] = 256;
		adjMat[38][103] = 249;
		adjMat[38][109] = 190;
		adjMat[39][98] = 446;
		adjMat[41][97] = 104;
		adjMat[41][98] = 169;
		adjMat[42][51] = 741;
		adjMat[42][85] = 154;
		adjMat[43][46] = 275;
		adjMat[43][84] = 486;
		adjMat[44][65] = 58;
		adjMat[44][109] = 277;
		adjMat[45][49] = 137;
		adjMat[45][103] = 276;
		adjMat[46][86] = 124;
		adjMat[46][89] = 182;
		adjMat[48][77] = 150;
		adjMat[49][58] = 210;
		adjMat[52][108] = 67;
		adjMat[53][100] = 82;
		adjMat[55][110] = 463;
		adjMat[56][96] = 29;
		adjMat[57][67] = 132;
		adjMat[59][74] = 110;
		adjMat[59][94] = 92;
		adjMat[60][68] = 268;
		adjMat[61][69] = 101;
		adjMat[62][76] = 174;
		adjMat[62][79] = 92;
		adjMat[63][87] = 8;
		adjMat[63][88] = 42;
		adjMat[64][103] = 105;
		adjMat[66][98] = 84;
		adjMat[66][108] = 168;
		adjMat[67][101] = 269;
		adjMat[68][97] = 120;
		adjMat[69][61] = 101;
		adjMat[69][71] = 319;
		adjMat[69][104] = 3548;
		adjMat[69][105] = 3548;
		adjMat[70][102] = 117;
		adjMat[70][111] = 178;
		adjMat[72][77] = 215;
		adjMat[72][81] = 115;
		adjMat[73][82] = 47;
		adjMat[73][93] = 174;
		adjMat[78][81] = 133;
		adjMat[78][84] = 520;
		adjMat[79][107] = 105;
		adjMat[81][87] = 95;
		adjMat[81][96] = 51;
		adjMat[83][88] = 31;
		adjMat[83][89] = 137;
		adjMat[86][111] = 172;
		adjMat[91][99] = 442;
		adjMat[91][101] = 436;
		adjMat[93][106] = 115;
		adjMat[99][110] = 440;

		adjMat[47][0] = 106;
		adjMat[97][0] = 120;
		adjMat[11][1] = 166;
		adjMat[57][1] = 226;
		adjMat[80][1] = 148;
		adjMat[26][2] = 267;
		adjMat[90][2] = 61;
		adjMat[15][3] = 117;
		adjMat[47][3] = 82;
		adjMat[14][4] = 161;
		adjMat[92][4] = 131;
		adjMat[34][5] = 186;
		adjMat[85][5] = 79;
		adjMat[29][6] = 107;
		adjMat[46][6] = 112;
		adjMat[69][7] = 102;
		adjMat[107][7] = 45;
		adjMat[40][8] = 50;
		adjMat[60][8] = 80;
		adjMat[34][9] = 69;
		adjMat[40][9] = 122;
		adjMat[73][10] = 428;
		adjMat[84][10] = 349;
		adjMat[74][11] = 51;
		adjMat[18][12] = 191;
		adjMat[80][12] = 164;
		adjMat[80][12] = 64;
		adjMat[101][12] = 105;
		adjMat[101][12] = 105;
		adjMat[106][13] = 605;
		adjMat[110][13] = 829;
		adjMat[33][14] = 91;
		adjMat[58][15] = 129;
		adjMat[53][16] = 279;
		adjMat[54][16] = 90;
		adjMat[22][17] = 56;
		adjMat[35][17] = 110;
		adjMat[20][18] = 142;
		adjMat[71][18] = 157;
		adjMat[24][19] = 70;
		adjMat[90][19] = 316;
		adjMat[22][20] = 72;
		adjMat[24][21] = 792;
		adjMat[50][21] = 83;
		adjMat[36][23] = 92;
		adjMat[66][23] = 54;
		adjMat[31][24] = 246;
		adjMat[109][24] = 523;
		adjMat[55][25] = 246;
		adjMat[65][25] = 135;
		adjMat[85][26] = 580;
		adjMat[102][26] = 320;
		adjMat[48][27] = 165;
		adjMat[82][27] = 63;
		adjMat[69][28] = 3939;
		adjMat[56][29] = 109;
		adjMat[64][30] = 209;
		adjMat[75][31] = 220;
		adjMat[54][32] = 117;
		adjMat[55][32] = 304;
		adjMat[76][33] = 74;
		adjMat[50][34] = 165;
		adjMat[95][35] = 246;
		adjMat[41][36] = 113;
		adjMat[92][36] = 140;
		adjMat[72][37] = 5131;
		adjMat[89][37] = 5451;
		adjMat[95][38] = 256;
		adjMat[103][38] = 249;
		adjMat[109][38] = 190;
		adjMat[98][39] = 446;
		adjMat[97][41] = 104;
		adjMat[98][41] = 169;
		adjMat[51][42] = 741;
		adjMat[85][42] = 154;
		adjMat[46][43] = 275;
		adjMat[84][43] = 486;
		adjMat[65][44] = 58;
		adjMat[109][44] = 277;
		adjMat[49][45] = 137;
		adjMat[103][45] = 276;
		adjMat[86][46] = 124;
		adjMat[89][46] = 182;
		adjMat[77][48] = 150;
		adjMat[58][49] = 210;
		adjMat[108][52] = 67;
		adjMat[100][53] = 82;
		adjMat[110][55] = 463;
		adjMat[96][56] = 29;
		adjMat[67][57] = 132;
		adjMat[74][59] = 110;
		adjMat[94][59] = 92;
		adjMat[68][60] = 268;
		adjMat[69][61] = 101;
		adjMat[76][62] = 174;
		adjMat[79][62] = 92;
		adjMat[87][63] = 8;
		adjMat[88][63] = 42;
		adjMat[103][64] = 105;
		adjMat[98][66] = 84;
		adjMat[108][66] = 168;
		adjMat[101][67] = 269;
		adjMat[97][68] = 120;
		adjMat[61][69] = 101;
		adjMat[71][69] = 319;
		adjMat[104][69] = 3548;
		adjMat[105][69] = 3548;
		adjMat[102][70] = 117;
		adjMat[111][70] = 178;
		adjMat[77][72] = 215;
		adjMat[81][72] = 115;
		adjMat[82][73] = 47;
		adjMat[93][73] = 174;
		adjMat[81][78] = 133;
		adjMat[84][78] = 520;
		adjMat[107][79] = 105;
		adjMat[87][81] = 95;
		adjMat[96][81] = 51;
		adjMat[88][83] = 31;
		adjMat[89][83] = 137;
		adjMat[111][86] = 172;
		adjMat[99][91] = 442;
		adjMat[101][91] = 436;
		adjMat[106][93] = 115;
		adjMat[110][99] = 440;
	}

	// Method to get the name of the city against an Index
	public static String getCityName(City[] cities, int index) {
		if (cities.length > index && index >= 0)
			return cities[index].getCityName();
		else
			return "";
	}

	private static int getCityIndex(City[] cities, String cityName) {
		int index = -1;

		for (int i = 0; i < cities.length; i++)
			if (cities[i].getCityName().toLowerCase().compareTo(cityName) == 0) {
				index = i;
				break;
			}

		return index;
	}

	// Method to get the edge length of all adjacent nodes for a given source
	// node
	public static Map<Integer, Integer> getAdjacentNode(int sourceNodeIndex, int[][] adjMat) {
		Map<Integer, Integer> mapAdjNodes = new HashMap<Integer, Integer>();

		for (int i = 0; i < adjMat[sourceNodeIndex].length; i++)
			if (adjMat[sourceNodeIndex][i] > 0)
				mapAdjNodes.put(i, adjMat[sourceNodeIndex][i]);

		return mapAdjNodes;
	}

	// Method to get the edge length of all adjacent nodes for a given source
	// node
	public static Map<Integer, Integer> getAdjacentNode(int sourceNodeIndex, int[][] adjMat, boolean checkVisitedFlag,
			City[] cities) {
		Map<Integer, Integer> mapAdjNodes = new HashMap<Integer, Integer>();

		for (int i = 0; i < adjMat[sourceNodeIndex].length; i++)
			if (adjMat[sourceNodeIndex][i] > 0 && !cities[i].getIsVisited())
				mapAdjNodes.put(i, adjMat[sourceNodeIndex][i]);

		return mapAdjNodes;
	}

}