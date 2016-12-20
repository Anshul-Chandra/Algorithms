import java.io.BufferedInputStream;
import java.io.IOException;

public class Test {

	// Method to read a binary / text file from input stream
	public static void main(String[] args) {

		// Reading file from
		BufferedInputStream inputFromFile = new BufferedInputStream(System.in);

		try {
			int character = (int) inputFromFile.read();

			while (character != -1) {
				if (character != 13 && character != 10)
					System.out.println((char) character);

				// System.out.println(character + " " + (char)character);

				character = (int) inputFromFile.read();
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}

	}

}
