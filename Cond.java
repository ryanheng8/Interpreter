import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Condition nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Cond {

    // Initialize the possible child nodes
    Cmpr cm;
    Cond con;

    // Initialize boolean to differentiate between 'OR' or 'AND'
    Boolean or = false;
    Boolean and = false;

    /*
     * Function to parse the Condition nonterminal
     */
    void parse(Scanner S) {

        // Check which condition option to parse
        if (S.currentToken() == Core.NOT) {
            // Parse the condition
            S.nextToken();
            con = new Cond();
            con.parse(S);

        } else {
            // Parse the comparison
            cm = new Cmpr();
            cm.parse(S);
            // Check whether it is an 'OR' or 'AND'
            if (S.currentToken() == Core.OR) {
                or = true;
            } else if (S.currentToken() == Core.AND) {
                and = true;
            }
            // If there is another condition, parse it
            if (or || and) {
                // Parse the condition
                S.nextToken();
                con = new Cond();
                con.parse(S);
            }
        }
    }

    /*
     * Function to check the semantics for the Condition nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Checks whether to semantically check the comparison or the condition
        if (cm != null) {
            cm.semCheck(scopes);
        }
        if (con != null) {
            con.semCheck(scopes);
        }
    }

    /*
     * Function to print the Condition nonterminal
     */
    void print() {
        // Checks which condition option to print
        if (con != null && cm == null) {
            System.out.println("not");
            // Print the condition
            con.print();
        } else {
            // Print the comparison
            cm.print();
            // Checks for the 'or' or 'and'
            if (or) {
                System.out.println("or");
            } else if (and) {
                System.out.println("and");
            }
            // Prints the condition
            if (or || and) {
                con.print();
            }
        }
    }

    /*
     * Function to execute the Condition nonterminal
     */
    boolean execute() {
        // Initialize boolean to return
        boolean result;

        // Checks which condition option to execute
        if (con != null && cm == null) {
            result = !con.execute();
        } else if (or) {
            result = cm.execute() || con.execute();
        } else if (and) {
            result = cm.execute() && con.execute();
        } else {
            result = cm.execute();
        }

        return result;
    }
}