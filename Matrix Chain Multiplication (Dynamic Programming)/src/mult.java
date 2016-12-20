import java.util.Scanner;

public class mult {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		int n = scan.nextInt(); // no. of matrices
		
		int[] arr = new int[n + 1];
		
		int i = 0;
		
		while (scan.hasNext()) {
			if(i <= n)
				arr[i] = scan.nextInt(); 
			
			i++;
		}
		
		scan.close();
		
		// Defining matrices to hold the optimal cost and the optimal solutions
		int[][] matCost = new int[n][n];
		int[][] matSolution = new int[n][n];
		
		matrixChainMultiplication(n, arr, matCost, matSolution);
		
		String solution = solutionExtraction(matSolution, 0, n - 1);
		
		// Removing the first and last parenthesis since they are extra
		solution = solution.substring(solution.indexOf("(") + 1, solution.lastIndexOf(")"));
		
		System.out.println(solution.trim());
	}

	// Method to compute the optimal cost for matrix multiplication using bottom-up approach
	public static void matrixChainMultiplication(int n, int[] arr, int[][] matCost, int[][] matSolution){
		
		// generating the different length of pairs for multiplication
		for(int length = 2; length <= n; length++){
			
			for(int i = 0; i <= n - length; i++){
				
				int j = i + length - 1;
				
				matCost[i][j] = -1;
				
				for(int k = i; k < j; k++){
					int multiplicationCost = matCost[i][k] + matCost[k+1][j] + arr[i]*arr[k+1]*arr[j+1];
					
					if(matCost[i][j] > -1){
						if(matCost[i][j] > multiplicationCost){
							matCost[i][j] = multiplicationCost;
							matSolution[i][j] = k + 1;
						}
					}
					else{
						matCost[i][j] = multiplicationCost;
						matSolution[i][j] = k + 1;
					}
				}
				
			}
			
		}
		
	}
	
	// Method to extract the optimal solution for matrix chain multiplication
	public static String solutionExtraction(int[][] matSolutions, int row, int column){
		
		if(row == column)
			return "M" + String.valueOf(row + 1);
		else {		
			return "( " + solutionExtraction(matSolutions, row, matSolutions[row][column] - 1)
			+ " * " + solutionExtraction(matSolutions, matSolutions[row][column], column) + " )";
		}
	}	
}
