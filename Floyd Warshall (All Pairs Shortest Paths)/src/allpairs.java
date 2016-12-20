import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class allpairs {

	public static void main(String[] args) throws FileNotFoundException {

		File file = new File("Input.txt");

		Scanner scan = new Scanner(file);

		int n = scan.nextInt();

		// Create an Array of strings to hold the strings
		String[] words = new String[n];

		for (int i = 0; i < n; i++) {
			words[i] = scan.next();
		}

		int numQueries = scan.nextInt();

		Query[] queries = new Query[numQueries];

		for (int i = 0; i < numQueries; i++) {
			queries[i] = new Query(scan.next(), scan.next());
		}

		scan.close();

		// Creation of the adjacency matrix
		int[][] adjMatrix = new int[n][n];

		// Matrix to hold the distance between a pair of words
		int[][] cost = new int[n][n];

		// Matrix to hold the 'k' values to recover the shortest path between
		// two pair of words
		int[][] steps = new int[n][n];

		// Creating the adjacency matrix
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (i != j) {
					adjMatrix[i][j] = CompareWords(words[i], words[j]);
					cost[i][j] = CompareWords(words[i], words[j]);
				} else {
					adjMatrix[i][j] = 0;
					cost[i][j] = 0;
				}

		// Computing the all-pairs-shortest paths as per Floyd Warshall
		// algorithm
		ComputeAllPairsShortestPaths(n, adjMatrix, cost, steps);

		// Computing the average paths
		double averagePaths = ComputeAveragePathCost(cost);

		// Displaying the average paths formatted to a precision of 2 decimal
		// places
		DecimalFormat format = new DecimalFormat("##.##");
		System.out.print(format.format(averagePaths));

		for (int i = 0; i < queries.length; i++) {
			System.out.println("");
			// Getting the index of words that are part of the query
			int startIndex = getIndexOfWord(words, queries[i].sourceWord);
			int destinationIndex = getIndexOfWord(words, queries[i].destinationWord);

			if (startIndex != -1 && destinationIndex != -1)
				ComputePathLengths(cost, steps, words, startIndex, destinationIndex, true);
		}
	}

	/*
	 * Method to compute all pairs shortest paths using Floyd-Warshall Algorithm
	 */
	public static void ComputeAllPairsShortestPaths(int n, int[][] adjMat, int[][] cost, int[][] steps) {

		for (int k = 0; k < n; k++) {
			for (int row = 0; row < adjMat.length; row++) {
				for (int col = 0; col < adjMat.length; col++) {
					if (cost[row][k] + cost[k][col] < cost[row][col]) {
						cost[row][col] = cost[row][k] + cost[k][col];
						steps[row][col] = k + 1;
					}

				}
			}
		}

	}

	/*
	 * Method to calculate the average path cost
	 */
	public static double ComputeAveragePathCost(int[][] cost) {

		double wordsReached = 0;

		for (int i = 0; i < cost.length; i++)
			for (int j = 0; j < cost.length; j++)
				if (cost[i][j] != Integer.MAX_VALUE / 2)
					wordsReached++;

		return wordsReached / (double) cost.length;
	}

	public static void ComputePathLengths(int[][] cost, int[][] steps, String[] words, int source, int destination,
			boolean printPathCost) {

		// Find the total cost of the path
		int pathCost = cost[source][destination];

		if (pathCost == Integer.MAX_VALUE / 2 && pathCost != 0) {
			// No path exists between the two words
			System.out.print(words[source] + " " + words[destination] + " not reachable");
		} else {
			if (printPathCost) {
				System.out.print(pathCost + " " + words[source]);
				// System.out.print();
			}

			// Find the value of 'k'
			int k = steps[source][destination] - 1;

			if (k == -1) {
				// Print the destination
				System.out.print(" " + words[destination]);
			} else {
				// Compute path from 'source --> k'
				ComputePathLengths(cost, steps, words, source, k, false);
				// Compute path from 'k --> destination'
				ComputePathLengths(cost, steps, words, k, destination, false);
			}
		}

	}

	/*
	 * Method to compare two words and return the weight value calculated as per
	 * the difference in the non-matching characters
	 */
	public static int CompareWords(String word, String anotherWord) {

		// Check if the length of both the words is same
		if (word.length() == anotherWord.length()) {

			int countOfMismatch = 0;
			int weight = 0;

			// Iterating over all the characters
			for (int index = 0; index < word.length(); index++) {
				int difference = Math.abs(word.charAt(index) - anotherWord.charAt(index));

				if (difference > 0) {
					countOfMismatch++;
					weight = difference;
				}

				if (countOfMismatch > 1)
					return Integer.MAX_VALUE / 2; // break the loop if the
													// number of mismatches is
													// greater than 1
			}

			return weight; // return the difference between the two non-matching
							// characters
		}

		return Integer.MAX_VALUE / 2;
	}

	public static void printMatrix(int[][] mat, String[] words) {

		System.out.print("\t");
		for (int i = 0; i < words.length; i++)
			System.out.print(words[i] + "\t");

		System.out.println("");

		for (int i = 0; i < mat.length; i++) {
			System.out.print(words[i] + "\t");
			for (int j = 0; j < mat.length; j++)
				System.out.print(mat[i][j] + "\t");

			System.out.println("");
		}
	}

	/*
	 * Method to get the index of a given word
	 */
	public static int getIndexOfWord(String[] words, String word) {
		for (int i = 0; i < words.length; i++)
			if (word.compareToIgnoreCase(words[i]) == 0)
				return i;

		return -1;
	}

}

/*
 * Class to hold the pair of words for the query
 */
class Query {
	public String sourceWord;

	public String destinationWord;

	public Query(String sourceWord, String destionationWord) {
		this.sourceWord = sourceWord;
		this.destinationWord = destionationWord;
	}
}
