import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the If nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class If {

    // Initialize the possible child nodes
    Cond con;
    StmtSeq ss1;
    StmtSeq ss2;

    /*
     * Function to parse the If nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "if"
        ErrorCheck.errorCheck(S.currentToken(), Core.IF);
        S.nextToken();

        // Parse the condition
        con = new Cond();
        con.parse(S);

        // Checks if the token is "then"
        ErrorCheck.errorCheck(S.currentToken(), Core.THEN);
        S.nextToken();

        // Parse the statement sequence
        ss1 = new StmtSeq();
        ss1.parse(S);

        // Checks if there is an "ELSE" block
        if (S.currentToken() == Core.ELSE) {
            // Parse the second statement sequence
            S.nextToken();
            ss2 = new StmtSeq();
            ss2.parse(S);
        }

        // Checks if the token is "end"
        ErrorCheck.errorCheck(S.currentToken(), Core.END);
        S.nextToken();
    }

    /*
     * Function to check the semantics for the If nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check the semantics for the condition
        con.semCheck(scopes);
        // Add a new map for the scope of the if block
        HashMap<String, Core> ifBlock = new HashMap<String, Core>();
        scopes.push(ifBlock);
        // Check the semantics for the if statement sequence
        ss1.semCheck(scopes);
        scopes.pop();
        // If there is an else block statement sequence, check its semantics
        if (ss2 != null) {
            // Add a new map for the scope of the else block
            HashMap<String, Core> elseBlock = new HashMap<String, Core>();
            scopes.push(elseBlock);
            // Check the semantics for the else statement sequence
            ss2.semCheck(scopes);
            scopes.pop();
        }
    }

    /*
     * Function to print the If nonterminal
     */
    void print() {
        System.out.println("if ");
        // Print the condition
        con.print();
        System.out.println("then");
        // Print the statement sequence
        ss1.print();
        // If there is a second statement sequence (else block), print it
        if (ss2 != null) {
            System.out.println("else");
            ss2.print();
        }
        System.out.println("end");
    }

    /*
     * Function to execute the If nonterminal
     */
    void execute() {
        // Check if there is an else statement
        if (ss2 != null) {
            // Adds a new local memory scope
            Memory.addScope();

            // Checks the condition and executes the proper statement sequence
            if (con.execute()) {
                ss1.execute();
            } else {
                ss2.execute();
            }

            // Removes the local memory scope
            Memory.removeScope();
        } else {
            // Checks the condition and executes the proper statement sequence
            if (con.execute()) {
                // Adds a new local memory scope
                Memory.addScope();

                ss1.execute();
                
                // Removes the local memory scope
                Memory.removeScope();
            }
        }
    }
}