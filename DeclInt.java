import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Declaration Int nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class DeclInt {

    // Initialize the possible child nodes
    String id;

    /*
     * Function to parse the Declaration Int nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "integer"
        ErrorCheck.errorCheck(S.currentToken(), Core.INTEGER);
        S.nextToken();

        // Checks if the token is "ID"
        ErrorCheck.errorCheck(S.currentToken(), Core.ID);
        id = S.getId();
        S.nextToken();

        // Checks if the token is "semicolon"
        ErrorCheck.errorCheck(S.currentToken(), Core.SEMICOLON);
        S.nextToken();
    }

    /*
     * Function to check the semantics for the Declaration Int nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check if the ID has been declared already
        boolean declared = false;
        for (HashMap<String, Core> ids : scopes) {
            if (ids.containsKey(id)) {
                declared = true;
            }
        }
        // If the ID has already been declared, print error
        if (declared) {
            System.out.println("ERROR: ID '" + id + "' has already been declared!");
            System.exit(1);
        // Otherwise put the declaration in the most recent scope
        } else {
            scopes.peek().put(id, Core.INTEGER);
        }
    }

    /*
     * Function to print the Declaration Int nonterminal
     */
    void print() {
        System.out.println("integer " + id + ";");
    }

    /*
     * Function to execute the Declaration Integer nonterminal
     */
    void execute() {
        // Initializes the integer with value 0
        Memory.declare(id, Core.INTEGER);
        Memory.storeInteger(id, 0);
    }
}