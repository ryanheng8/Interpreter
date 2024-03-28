import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the In nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class In {

    // Initialize the possible child nodes
    String id;

    /*
     * Function to parse the In nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "in"
        ErrorCheck.errorCheck(S.currentToken(), Core.IN);
        S.nextToken();

        // Checks if the token is "lparen"
        ErrorCheck.errorCheck(S.currentToken(), Core.LPAREN);
        S.nextToken();

        // Checks if the token is "id"
        ErrorCheck.errorCheck(S.currentToken(), Core.ID);
        id = S.getId();
        S.nextToken();

        // Checks if the token is "rparen"
        ErrorCheck.errorCheck(S.currentToken(), Core.RPAREN);
        S.nextToken();

        // Checks if the token is "semicolon"
        ErrorCheck.errorCheck(S.currentToken(), Core.SEMICOLON);
        S.nextToken();
    }

    /*
     * Function to check the semantics for the In nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Checks if the ID has been declared
        boolean undeclared = true;
        for (HashMap<String, Core> ids : scopes) {
            if (ids.containsKey(id)) {
                undeclared = false;
            }
        }
        // If undeclared, print error message
        if (undeclared) {
            System.out.println("ERROR: ID '" + id + "' has not been declared in this scope!");
            System.exit(1);
        }
    }

    /*
     * Function to print the In nonterminal
     */
    void print() {
        // Print the in with the ID
        System.out.println("in(" + id + ");");
    }

    /*
     * Function to execute the In nonterminal
     */
    void execute() {
        // Check for file empty or file error
        if (Main.sd.currentToken() != Core.EOS && Main.sd.currentToken() != Core.ERROR) {
            // Declares the value for the ID information
            Memory.Value value = Memory.getValue(id);

            // Puts in the value based on integer or array
            if (value.type == Core.INTEGER) {
                value.intValue = Main.sd.getConst();
            } else if (value.type == Core.ARRAY && value.arrayValue != null) {
                value.arrayValue[0] = Main.sd.getConst();
            // Print error assigning to null array
            } else {
                System.out.println("ERROR: assigning the input value into a null array '" + id + "'!");
                System.exit(1);
            }

            // Consume the token
            Main.sd.nextToken();
        // Error has occured, print message and exit
        } else {
            System.out.println("ERROR: Data file does not have enough values or has an error!");
            System.exit(1);
        }
    }
}