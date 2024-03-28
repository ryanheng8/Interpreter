import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Class for the Scanner to convert an input file into a stream of tokens as well as the methods
 * to acces the tokens or get the ID or CONST value
 */
class Scanner {

    // Initializes max constant constraints
    private final int MAXNUM = 100003;
    private final int MAXSIZE = 6;

    // Initialize queues to store data from the file.
    private Queue<Core> tokens = new LinkedList<Core>();
    private Queue<String> identifiers = new LinkedList<String>();
    private Queue<Integer> constants = new LinkedList<Integer>();


    // Initialize the scanner
    Scanner(String filename) {

        // Initializes input stream
        BufferedReader inputFile;
        try {
            inputFile = new BufferedReader(new FileReader(filename));
            // Tokenize the file
            tokenize(inputFile);
	    // Close the file
	    try {
                inputFile.close();
            } catch (IOException e) {
                System.out.println("Error: File could not be closed successfully!");
            }
        } catch (IOException e) {
            System.out.println("Error: File could not be opened successfully!");
            tokens.add(Core.ERROR);
        }
    }

    /* 
    Reads the input file to find the tokens, identifiers, and constants to
    put into the respective queues.
    */
    private void tokenize(BufferedReader inputFile) {
        
	// Tries to read the file
        try {
            int chr = inputFile.read();
            // Loops until the end of the file.
            while (chr != -1) {
                // Skips whitespace
                if (!Character.isWhitespace((char) chr)) {
                    // Switches through the possible symbols
                    switch ((char) chr) {
                        case '+':
                            tokens.add(Core.ADD);
                            break;
                        case '-':
                            tokens.add(Core.SUBTRACT);
                            break;
                        case '*':
                            tokens.add(Core.MULTIPLY);
                            break;
                        case '/':
                            tokens.add(Core.DIVIDE);
                            break;
                        case ':':
                            // Checks for assign or just colon
                            inputFile.mark(1);
                            int peek = inputFile.read();
                            if (peek != -1 && (char) peek == '=') {
                                tokens.add(Core.ASSIGN);
                            } else {
                                inputFile.reset();
                                tokens.add(Core.COLON);
                            }
                            break;
                        case '=':
                            tokens.add(Core.EQUAL);
                            break;
                        case '<':
                            tokens.add(Core.LESS);
                            break;
                        case ';':
                            tokens.add(Core.SEMICOLON);
                            break;
                        case '.':
                            tokens.add(Core.PERIOD);
                            break;
                        case ',':
                            tokens.add(Core.COMMA);
                            break;
                        case '(':
                            tokens.add(Core.LPAREN);
                            break;
                        case ')':
                            tokens.add(Core.RPAREN);
                            break;
                        case '[':
                            tokens.add(Core.LBRACE);
                            break;
                        case ']':
                            tokens.add(Core.RBRACE);
                            break;
                        // Checks through the keywords, constants, and identifiers
                        default:
                            // Checks for a constant
                            if (Character.isDigit((char) chr)) {
                                StringBuilder num = new StringBuilder();
                                while (chr != -1 && Character.isDigit((char) chr)) {
                                    num.append((char) chr);
                                    // Leaves a mark in case the next character is not a digit.
                                    inputFile.mark(1);
                                    chr = inputFile.read();
                                }
                                inputFile.reset();
                                // Checks if the constant is has leading 0s
                                if (num.length() > 1 && num.charAt(0) == '0') {
                                    System.out.println("Error: '" + num.toString() + "' is an invalid constant (leading 0s)!");
                                    tokens.add(Core.ERROR);
                                // Checks if the constant is too large in size
                                } else if (num.length() > MAXSIZE) {
                                        System.out.println("Error: '" + num.toString() + "' is an invalid constant (too large)!");
                                        tokens.add(Core.ERROR);
                                } else {
                                    int number = Integer.parseInt(num.toString());
                                    // Checks if the number is too large in count
                                    if (number > MAXNUM) {
					System.out.println("Error: '" + number + "'' is an invalid constant (too large)!");
                                        tokens.add(Core.ERROR);
				    // Constant is valid
                                    } else {
                                        tokens.add(Core.CONST);
                                        constants.add(number);
                                    }
                                }
                            // Checks for keyword or identifier
                            } else if (Character.isLetter((char) chr)) {
                                StringBuilder wordBuilder = new StringBuilder();
                                while (chr != -1 && Character.isLetterOrDigit((char) chr)) {
                                    wordBuilder.append((char) chr);
                                    // Leaves a mark in case the next character is not a letter or digit.
                                    inputFile.mark(1);
                                    chr = inputFile.read();
                                }
                                inputFile.reset();
                                String word = wordBuilder.toString();
				// Switches through the possible keywords
                                switch (word) {
                                    case "procedure":
                                        tokens.add(Core.PROCEDURE);
                                        break;
                                    case "begin":
                                        tokens.add(Core.BEGIN);
                                        break;
                                    case "is":
                                        tokens.add(Core.IS);
                                        break;
                                    case "end":
                                        tokens.add(Core.END);
                                        break;
                                    case "if":
                                        tokens.add(Core.IF);
                                        break;
                                    case "else":
                                        tokens.add(Core.ELSE);
                                        break;
                                    case "in":
                                        tokens.add(Core.IN);
                                        break;
                                    case "integer":
                                        tokens.add(Core.INTEGER);
                                        break;
                                    case "return":
                                        tokens.add(Core.RETURN);
                                        break;
                                    case "do":
                                        tokens.add(Core.DO);
                                        break;
                                    case "new":
                                        tokens.add(Core.NEW);
                                        break;
                                    case "not":
                                        tokens.add(Core.NOT);
                                        break;
                                    case "and":
                                        tokens.add(Core.AND);
                                        break;
                                    case "or":
                                        tokens.add(Core.OR);
                                        break;
                                    case "out":
                                        tokens.add(Core.OUT);
                                        break;
                                    case "array":
                                        tokens.add(Core.ARRAY);
                                        break;
                                    case "then":
                                        tokens.add(Core.THEN);
                                        break;
                                    case "while":
                                        tokens.add(Core.WHILE);
                                        break;
                                    // Word must be an identifier
                                    default:
                                        tokens.add(Core.ID);
                                        identifiers.add(word);
                                }
                            // Must be invalid character
                            } else {
                                System.out.println("Error: '" + (char) chr + "' is an invalid character in the file!");
                                tokens.add(Core.ERROR);
                            }
                    }
                
		}
		// Reads the next character
                chr = inputFile.read();
            }
            // End of the file has been reached
            tokens.add(Core.EOS);
        } catch (IOException e) {
            System.out.println("Error: File could not be read.");
            tokens.add(Core.ERROR);
        }
    }

    // Advance to the next token
    public void nextToken() {
        // Removes the token from the queue.
        tokens.poll();
    }

    // Return the current token
    public Core currentToken() {
        // Retrieves but does not remove the token from the queue.
        return tokens.peek();
    }

	// Return the identifier string
    public String getId() {
        // Retrieves and removes the identifier from the queue.
        return identifiers.poll();
    }

	// Return the constant value
    public int getConst() {
        // Retrieves and removes the constant from the queue.
        return constants.poll();
    }

}
