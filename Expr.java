import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Expression nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Expr {

    // Initialize the possible child nodes
    Term t;
    Expr ex;

    // Initialize boolean to differentiate between 'ADD' or 'SUBTRACT'
    Boolean add = false;
    Boolean subtract = false;

    /*
     * Function to parse the Expression nonterminal
     */
    void parse(Scanner S) {
        // Parse the term
        t = new Term();
        t.parse(S);

        // Check if there is an expression
        if (S.currentToken() == Core.ADD || S.currentToken() == Core.SUBTRACT) {
            // Checks which operator it is
            if (S.currentToken() == Core.ADD) {
                add = true;
            } else if (S.currentToken() == Core.SUBTRACT) {
                subtract = true;
            }
            // Parse the expression
            S.nextToken();
            ex = new Expr();
            ex.parse(S);
        }
    }

    /*
     * Function to check the semantics for the Expression nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Semantically check the term
        t.semCheck(scopes);
        // Check if there is an expression to semantically check
        if (ex != null) {
            ex.semCheck(scopes);
        }
    }

    /*
     * Function to print the Expression nonterminal
     */
    void print() {
        // Print the term
        t.print();
        // Check if there is an expression to print
        if (ex != null) {
            if (add) {
                System.out.println("+");
            } else if (subtract) {
                System.out.println("-");
            }
            // Print the expression
            ex.print();
        }
    }

    /*
     * Function to execute the Expression nonterminal
     */
    int execute() {
        int expression = 0;
        // Execute the term
        expression = t.execute();
        // Check if there is an expression to execute
        if (ex != null) {
            if (add) {
                expression += ex.execute();
            } else if (subtract) {
                expression -= ex.execute();
            }
        }
        return expression;
    }
}