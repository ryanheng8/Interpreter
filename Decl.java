import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Declaration nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Decl {

    // Initialize the possible child nodes
    DeclInt di;
    DeclArr da;

    /*
     * Function to parse the Declaration nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is an "INTEGER". If not, must be "ARRAY"
        if (S.currentToken() == Core.INTEGER) {
            // Parse the DeclInt
            di = new DeclInt();
            di.parse(S);
        } else if (S.currentToken() == Core.ARRAY) {
            // Parse the DeclArr
            da = new DeclArr();
            da.parse(S);
        } else {
            System.out.println("ERROR: Expecting 'INTEGER' or 'ARRAY', but recieved, '" + S.currentToken() + "'!");
            System.exit(1);
        }
    }

    /*
     * Function to check the semantics for the Declaration nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Checks whether to semantically check the DeclInt or DeclArr
        if (di != null) {
            di.semCheck(scopes);
        } else if (da != null) {
            da.semCheck(scopes);
        }
    }

    /*
     * Function to print the Declaration nonterminal
     */
    void print() {
        // Checks whether to print the DeclInt or DeclArr
        if (di != null) {
            di.print();
        } else if (da != null) {
            da.print();
        }
    }

    /*
     * Function to execute the Declaration nonterminal
     */
    void execute() {
        // Checks whether to execute the DeclInt or DeclArr
        if (di != null) {
            di.execute();
        } else if (da != null) {
            da.execute();
        }
    }
}