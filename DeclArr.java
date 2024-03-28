import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Declaration Array nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class DeclArr {

    // Initialize the possible child nodes
    String id;

    /*
     * Function to parse the Declaration Array nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "array"
        ErrorCheck.errorCheck(S.currentToken(), Core.ARRAY);
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
     * Function to check the semantics for the Declaration Array nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Checks if the ID has already been declared
        boolean declared = false;
        for (HashMap<String, Core> ids : scopes) {
            if (ids.containsKey(id)) {
                declared = true;
            }
        }
        // If the ID has already been declared, print error and exit
        if (declared) {
            System.out.println("ERROR: ID '" + id + "' has already been declared!");
            System.exit(1);
        // Otherwise add the ID to the most recent scope
        } else {
            scopes.peek().put(id, Core.ARRAY);
        }
    }

    /*
     * Function to print the Declaration Array nonterminal
     */
    void print() {
        System.out.println("array " + id + ";");
    }

    /*
     * Function to execute the Declaration Array nonterminal
     */
    void execute() {
        Memory.declare(id, Core.ARRAY);
    }
}