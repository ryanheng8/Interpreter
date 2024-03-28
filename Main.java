/*
 * This is the main class that instantiates the Scanner, parses, semantically checks, executes, 
 * and prints the input file
 */
class Main {

	// Declares a scanner to read in the data
	static Scanner sd;
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		Scanner S = new Scanner(args[0]);

		// Initialize the scanner with the data file
		sd = new Scanner(args[1]);

		// Check for file empty or file error
		if (S.currentToken() != Core.EOS && S.currentToken() != Core.ERROR) {
			// Start a new procedure to recursively parse and execute
			Procedure p = new Procedure();
			p.parse(S);
			p.execute();
		// Error has occured, print message
		} else {
			System.out.println("ERROR: File is empty or has an error!");
		}
	}
}