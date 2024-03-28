import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Comparison nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Cmpr {

    // Initialize the possible child nodes
    Expr ex1;
    Expr ex2;

    // Initialize boolean to differentiate between equality or less than
    Boolean equal = false;

    /*
     * Function to parse the Comparison nonterminal
     */
    void parse(Scanner S) {
        // Parse the first expression
        ex1 = new Expr();
        ex1.parse(S);

        // Check if there is an expression to parse
        if (S.currentToken() == Core.EQUAL || S.currentToken() == Core.LESS) {
            // Checks for equal or less than
            if (S.currentToken() == Core.EQUAL) {
                equal = true;
            }

            // Parse the second expression
            S.nextToken();
            ex2 = new Expr();
            ex2.parse(S);
        // Print error missing comparison operator
        } else {
            System.out.println("ERROR: Expecting 'EQUAL' or 'LESS', but recieved '" + S.currentToken() + "'!");
            System.exit(1);
        }
    }

    /*
     * Function to check the semantics for the Comparison nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check the semantics for both expressions
        ex1.semCheck(scopes);
        ex2.semCheck(scopes);
    }

    /*
     * Function to print the Comparison nonterminal
     */
    void print() {
        // Print the expression
        ex1.print();
        // Check if it is equals or less than
        if (equal) {
            System.out.println("=");
        } else {
            System.out.println("<");
        }
        // Print the expression
        ex2.print();
    }

    /*
     * Function to execute the Comparison nonterminal
     */
    boolean execute() {
        // Initialize the boolean to return
        boolean result;

        // Get the expression values
        int value1 = ex1.execute();
        int value2 = ex2.execute();

        // Check if it is equals or less than
        if (equal) {
            result = value1 == value2;
        } else {
            result = value1 < value2;
        }

        return result;
    }
}