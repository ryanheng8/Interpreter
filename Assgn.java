import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Assign nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Assgn {

    // Initialize the possible child nodes
    String id1;
    String id2;
    Expr ex1;
    Expr ex2;

    // Boolean to differentiate between integer and expression
    Boolean integer = false;

    /*
     * Function to parse the Assign nonterminal
     */
    void parse(Scanner S) {

        // Checks if the token is "id"
        ErrorCheck.errorCheck(S.currentToken(), Core.ID);
        id1 = S.getId();
        S.nextToken();

        // Checks if there is indexing
        if (S.currentToken() == Core.LBRACE) {
            // Parse the first expression
            S.nextToken();
            ex1 = new Expr();
            ex1.parse(S);

            // Checks if the token is "rbrace"
            ErrorCheck.errorCheck(S.currentToken(), Core.RBRACE);
            S.nextToken();
        }

        // Checks if the token is "assign"
        ErrorCheck.errorCheck(S.currentToken(), Core.ASSIGN);
        S.nextToken();

        // Checks for a new integer
        if (S.currentToken() == Core.NEW) {
            // Consume the new token
            S.nextToken();

            // Checks if the token is "integer"
            ErrorCheck.errorCheck(S.currentToken(), Core.INTEGER);
            S.nextToken();

            // Set the boolean to true to denote a new integer
            integer = true;

            // Checks if the token is "lbrace"
            ErrorCheck.errorCheck(S.currentToken(), Core.LBRACE);
            S.nextToken();

            // Parse the second expression
            ex2 = new Expr();
            ex2.parse(S);

            // Checks if the token is "rbrace"
            ErrorCheck.errorCheck(S.currentToken(), Core.RBRACE);
            S.nextToken();

        // Checks for array
        } else if (S.currentToken() == Core.ARRAY) {
            // Consume the array token
            S.nextToken();

            // Checks if the token is "id"
            ErrorCheck.errorCheck(S.currentToken(), Core.ID);
            id2 = S.getId();
            S.nextToken();

        // Must be an expression
        } else {
            // Parse the second expression
            ex2 = new Expr();
            ex2.parse(S);
        }

        // Checks if the token is "semicolon"
        ErrorCheck.errorCheck(S.currentToken(), Core.SEMICOLON);
        S.nextToken();
    }

    /*
     * Function to check the semantics for the Assign nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Initializes variables for ID information
        Core type1 = null;
        boolean undeclared = true;

        // Checks if the ID has been declared
        for (HashMap<String, Core> ids : scopes) {
            if (ids.containsKey(id1)) {
                undeclared = false;
                type1 = ids.get(id1);
            }
        }
        // Prints error and exits if ID is undeclared
        if (undeclared) {
            System.out.println("ERROR: ID '" + id1 + "' has not been declared in this scope!");
            System.exit(1);
        }

        // Checks for which type of assign statement
        if (id2 != null) {
            // Initializes variables for the second ID
            Core type2 = null;
            boolean undeclared2 = true;

            // Checks if the second ID has been declared
            for (HashMap<String, Core> ids : scopes) {
                if (ids.containsKey(id2)) {
                    undeclared2 = false;
                    type2 = ids.get(id2);
                }
            }
            // Prints error and exits if ID is undeclared
            if (undeclared2) {
                System.out.println("ERROR: ID '" + id2 + "' has not been declared in this scope!");
                System.exit(1);
            }

            // Checks that the types are both array before trying to assign them
            if ((type1 != Core.ARRAY) || (type2 != Core.ARRAY)) {
                System.out.println("ERROR: '" + id1 + "' (type '" + type1 + "') and '" + id2 + "' (type '" + type2 + "') both need to be of type ARRAY!");
                System.exit(1);
            }
        } else if (ex1 != null && ex2 != null) {
            // Checks that the ID is an array to index
            if (type1 != Core.ARRAY) {
                System.out.println("ERROR: Trying to take index of type 'ARRAY', but '" + id1 + "' is of type '" + type1 + "'!");
                System.exit(1);
            } else {
                ex1.semCheck(scopes);
                ex2.semCheck(scopes);
            }
        } else if (integer) {
            // Checks if the ID is an array to assign
            if (type1 != Core.ARRAY) {
                System.out.println("ERROR: Expecting type 'ARRAY', but '" + id1 + "' is of type '" + type1 + "'!");
                System.exit(1);
            } else {
                ex2.semCheck(scopes);
            }
        } else {
            ex2.semCheck(scopes);
        }
    }

    /*
     * Function to print the assign nonterminal
     */
    void print() {
        System.out.println(id1);
        // Checks for the different options of assign statements and prints them
        if (id2 != null) {
            System.out.print(":= array " + id2 + ";");
        } else if (ex1 != null && ex2 != null) {
            System.out.println("[ ");
            // Print the first expression
            ex1.print();
            System.out.println("] := ");
            // Print the second expression
            ex2.print();
            System.out.println(";");
        } else if (integer) {
            System.out.println(":= new integer [");
            // Print the expression
            ex2.print();
            System.out.println("];");
        } else {
            System.out.println(":=");
            // Print the expression
            ex2.print();
            System.out.println(";");
        }
    }

    /*
     * Function to execute the assign nonterminal
     */
    void execute() {
        // Initializes value for ID information
        Memory.Value value = Memory.getValue(id1);

        // Checks for which type of assign statement
        if (id2 != null) {
            // Assigns id2 to id1
            Memory.storeArray(id1, id2);

        } else if (ex1 != null && ex2 != null) {
            // Stores the value of ex2 into the position of ex1 in id1
            int index = ex1.execute();

            // Checks if the index is in range of the array
            if (value.arrayValue != null && index < (value.arrayValue.length - 1) && index >= 0) {
                Memory.storeIndex(id1, index, ex2.execute());
            // Print error and exit
            } else {
                System.out.println("ERROR: Attempting to access index '" + index + "', but is out of bounds for array '" + id1 + "', is null, or is not an array!");
                System.exit(1);
            }

        } else if (integer) {
            // Checks if the expression is greater than 0 to initialize the array
            int size = ex2.execute();
            if (size > 0) {
                Memory.initArray(id1, size);
            } else {
                System.out.println("ERROR: Size '" + size + "' for array '" + id1 + "' is not a natural number (greater than 0)!");
                System.exit(1);
            }
        } else {
            // Checks whether to assign the value based on integer or array[0]
            if (value.type == Core.INTEGER) {
                Memory.storeInteger(id1, ex2.execute());
            } else if (value.type == Core.ARRAY && value.arrayValue != null) {
                Memory.storeIndex(id1, 0, ex2.execute());
            // Prints error for null array and exits
            } else {
                System.out.println("ERROR: Can not assign a value to a null array '" + id1 + "'!");
                System.exit(1);
            }
        }
    }

}