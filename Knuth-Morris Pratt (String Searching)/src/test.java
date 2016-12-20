
public class test {

	public static void main(String[] args){
		
		String key = "251220825122082";
		
		//key = "123123123";
		
		int length = findPatternLength(key);
		
		System.out.println(length);
	}
	
	
	public static int findPatternLength(String key) {
		int[] repeats = new int[key.length()];
		
		repeats[0] = 0;
		
		int length = 0;
		
		int c = -1;

		
		for (int i = 1; i < key.length(); i++) {

			while (c >= 0 && key.charAt(c + 1) != key.charAt(i)) {
				// Find the index of the last matching pattern
				c = repeats[c] - 1;
			}

			// Increment the value of 'c' in case the characters match
			if (key.charAt(c + 1) == key.charAt(i)) {
				c++;
				
				if(repeats[i - 1] == c){
					if(key.length() % (length + 1) < key.length())
						length++;
				}
			}

			// Set the prefix index
			repeats[i] = c + 1;
		}
		
		
		return length;
	}	
}
