import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class kmp {

	public static void main(String[] args) throws FileNotFoundException {
		long startTime = 0;
		long endTime = 0;
		int index = 0;

		String needle = null;
		String haystack = null;
		String filename = null;

		// Reading the file name from the command line argument (if exists)
		if (args != null && args.length > 0) {
			filename = args[0];
		}

		if (filename != null) {
			// Get the 'needle' and 'haystack' from the file
			File file = new File(filename);

			Scanner scan = new Scanner(file);

			haystack = scan.nextLine();

			needle = scan.next();

			scan.close();
		} else {
			// Generate new strings for needle and haystack
			GenerateTestData objTestData = null;
			
			// String set 1
			objTestData = generateStringsForKMPAnalysis();

			
			// String set 2 - Uncomment the line below to test the second set
			// objTestData = generateStringsForKMPAnalysis2();

			needle = objTestData.getNeedle();
			haystack = objTestData.getHaystack();
		}

		if (needle != null && haystack != null) {

			
			// Naive method
			startTime = System.currentTimeMillis();

			index = naive(needle, haystack);
			System.out.print("found at: " + index + "\r\n");

			endTime = System.currentTimeMillis();

			System.out.print("naive search time: " + (endTime - startTime) + "\r\n");
			
			/*_______________________________________________________________________*/
			
			// Standard Algorithm (Java)
			startTime = System.currentTimeMillis();

			index = haystack.indexOf(needle);

			System.out.print("found at: " + index + "\r\n");

			endTime = System.currentTimeMillis();
			System.out.print("standard search time: " + (endTime - startTime) + "\r\n");
			
			/*_______________________________________________________________________*/

			// KMP algorithm
			startTime = System.currentTimeMillis();

			index = kmpStringSearch(needle, haystack);

			System.out.print("found at: " + index + "\r\n");

			endTime = System.currentTimeMillis();
			System.out.print("kmp search time: " + (endTime - startTime));
		}
	}

	/*
	 * Method that implements a naive algorithm to search for a sub-string in a
	 * string
	 */
	public static int naive(String needle, String haystack) {

		int count = 0;
		int position = -1;

		for (int i = 9; i < haystack.length(); i++) {

			int j = i - 1;
			count = -1;

			while (j < (haystack.length() - 1) && count < (needle.length() - 1)
					&& haystack.charAt(j + 1) == needle.charAt(count + 1)) {
				j++;
				count++;
			}

			if (count == needle.length() - 1)
				return i;
		}

		return position;
	}

	/*
	 * Method to find the substring position using KMP algorithm
	 * Returns the zero based index of the needle string in the haystack string
	 */
	public static int kmpStringSearch(String needle, String haystack) {

		int[] prefix = new int[needle.length()];

		// Generate the prefix array for the needle
		generatePrefixArray(prefix, needle);

		int c = -1;

		for (int i = 0; i < haystack.length(); i++) {

			while (c >= 0 && needle.charAt(c + 1) != haystack.charAt(i)) {
				// move the index for the needle to the previous matching characters
				c = prefix[c] - 1;
			}

			if (needle.charAt(c + 1) == haystack.charAt(i)){
				// Increment the needle index in case a match is found
				c++;
			}

			if (c == needle.length() - 1) {
				// We've found the needle in the haystack. Return the starting index
				return (i - needle.length() + 1);
			}
		}

		return -1;
	}

	/*
	 * Method to calculate the prefix function used in KMP algorithm
	 */
	public static void generatePrefixArray(int[] prefix, String needle) {
		prefix[0] = 0;

		int c = -1;

		for (int i = 1; i < needle.length(); i++) {

			while (c >= 0 && needle.charAt(c + 1) != needle.charAt(i)) {
				// Find the index of the last matching pattern
				c = prefix[c] - 1;
			}

			// Increment the value of 'c' in case the characters match
			if (needle.charAt(c + 1) == needle.charAt(i)) {
				c++;
			}

			// Set the prefix index
			prefix[i] = c + 1;
		}
	}

	public static GenerateTestData generateStringsForKMPAnalysis() {

		GenerateTestData objTestData = new GenerateTestData();

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < 50; i++) {
			builder.append("a");
		}

		builder.append("b");

		objTestData.setNeedle(builder.toString());

		builder = new StringBuilder();

		for (int i = 0; i < 700000; i++) {
			builder.append("a");
		}

		builder.append(objTestData.getNeedle());

		objTestData.setHaystack(builder.toString());

		return objTestData;
	}

	public static GenerateTestData generateStringsForKMPAnalysis2() {

		GenerateTestData objTestData = new GenerateTestData();

		// use a prefix pattern to repeat
		String smallPattern = "aababcdaascaababcdadaababcdaaaababcdaabab";

		// Making a long pattern that consists of repeating small patterns
		String pattern = smallPattern + smallPattern + smallPattern;

		// Appending a random character to the pattern generated to come up with the needle
		objTestData.setNeedle(pattern + "@");

		// Generate the needle by using the small pattern
		StringBuilder builder = new StringBuilder();

		Random r = new Random();

		int max = 122;
		int min = 97;

		for (int i = 0; i < 700000; i++) {
			int num = r.nextInt((max - min) + 1) + min;

			// Randomly append a character
			builder.append((char) num);

			// Add the pattern to the builder
			builder.append(pattern);
		}

		builder.append(objTestData.getNeedle());

		objTestData.setHaystack(builder.toString());

		return objTestData;
	}

}

/*
 * Class to hold the test data string for haystack and needle
 */
class GenerateTestData {

	private String haystack;

	private String needle;

	public void setHaystack(String val) {
		this.haystack = val;
	}

	public void setNeedle(String val) {
		this.needle = val;
	}

	public String getHaystack() {
		return this.haystack;
	}

	public String getNeedle() {
		return this.needle;
	}
}
