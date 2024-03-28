import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Factor nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Factor {

    // Initialize the possible child nodes
    String id;
    int cons;
    Expr ex;

    /*
     * Function to parse the Factor nonterminal
     */
    void parse(Scanner S) {

        // Parse the ID factor option
        if (S.currentToken() == Core.ID) {
            id = S.getId();
            S.nextToken();

            // Check if there is an expression with the ID
            if (S.currentToken() == Core.LBRACE) {
                // Parse the expression
                S.nextToken();
                ex = new Expr();
                ex.parse(S);

                // Checks if the token is "rbrace"
                ErrorCheck.errorCheck(S.currentToken(), Core.RBRACE);
                S.nextToken();
            }
        // Parse the constant factor option
        } else if (S.currentToken() == Core.CONST) {
            cons = S.getConst();
            S.nextToken();
        // Parse the expression factor option
        } else if (S.currentToken() == Core.LPAREN) {
            // Consumes the token
            S.nextToken();

            // Parse the expression
            ex = new Expr();
            ex.parse(S);

            // Checks if the token is "rparen"
            ErrorCheck.errorCheck(S.currentToken(), Core.RPAREN);
            S.nextToken();
        // Print error message and exit
        } else {
            System.out.println("ERROR: Expecting an ID, CONSTANT, or EXPRESSION, but recieved '" + S.currentToken() + "'!");
            System.exit(1);
        }
    }

    /*
     * Function to check the semantics for the Factor nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Initialize ID information
        boolean undeclared = true;
        Core type = null;

        // Check the semantics of the ID option
        if (id != null) {
            // Check if the ID has been declared
            for (HashMap<String, Core> ids : scopes) {
                if (ids.containsKey(id)) {
                    undeclared = false;
                    type = ids.get(id);
                }
            }
            // If undeclared, print error message and exit
            if (undeclared) {
                System.out.println("ERROR: ID '" + id + "' has not been declared in this scope!");
                System.exit(1);
            }
            // Check the sematics for an expression with the ID option
            if (ex != null) {
                // Check if the ID type is ARRAY
                if (type != Core.ARRAY) {
                    System.out.println("ERROR: Can not retrieve index of '" + id + "' as it is not an ARRAY!");
                    System.exit(1);
                }
                // Semantically check the expression
                ex.semCheck(scopes);
            }
        // Sematically check the expression
        } else if (ex != null) {
            ex.semCheck(scopes);
        }
    }

    /*
     * Function to print the Factor nonterminal
     */
    void print() {
        // Checks which factor option to print
        if (id != null) {
            System.out.println(id);
            if (ex != null) {
                System.out.println("[");
                // Print the expression
                ex.print();
                System.out.println("]");
            }
        } else if (ex != null) {
            System.out.println("(");
            // Print the expression
            ex.print();
            System.out.println(")");
        } else {
            System.out.println(cons);
        }
    } 

    /*
     * Function to execute the Factor nonterminal
     */
    int execute() {
        // Initialize the value to return
        int result = 0;

        // Checks which factor option to execute
        if (id != null) {
            // Declare value for the ID information
            Memory.Value value = Memory.getValue(id);

            // Checks if there is an index for the id
            if (ex != null) {
                int index = ex.execute();
                // Get the array value
                if (value.arrayValue != null && index < (value.arrayValue.length - 1) && index >= 0) {
                    result = value.arrayValue[index];
                // Error accessing null array
                } else {
                    System.out.println("ERROR: Attempting to access index '" + index + "', but is out of bounds for array '" + id + "', is null, or is not an array!");
                    System.exit(1);
                }
            } else {
                // Checks whether to return the value from the int or array
                if (value.type == Core.INTEGER) {
                    result = value.intValue;
                } else if (value.type == Core.ARRAY && value.arrayValue != null) {
                    result = value.arrayValue[0];
                // Print null array error and exit
                } else {
                    System.out.println("ERROR: Attemping to access an index in a null array '" + id + "'!");
                    System.exit(1);
                }
            }
        // Check if the factor is an expression
        } else if (ex != null) {
            // Execute the expression
            result = ex.execute();
        // The factor must be a constant
        } else {
            result = cons;
        }

        return result;
    } 
}