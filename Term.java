import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Term nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Term {

    // Initialize the possible child nodes
    Factor f;
    Term t;

    // Initialize boolean to differentiate between multiplication or division
    Boolean mult = false;
    Boolean divide = false;

    /*
     * Function to parse the Term nonterminal
     */
    void parse(Scanner S) {
        // Parse the factor
        f = new Factor();
        f.parse(S);

        // Check if there is a term
        if (S.currentToken() == Core.MULTIPLY || S.currentToken() == Core.DIVIDE) {
            // Set the boolean for multiply or divide
            if (S.currentToken() == Core.MULTIPLY) {
                mult = true;
            } else if (S.currentToken() == Core.DIVIDE) {
                divide = true;
            }
            // Parse the term
            S.nextToken();
            t = new Term();
            t.parse(S);
        }
    }

    /*
     * Function to check the semantics for the Term nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check the factor semantics
        f.semCheck(scopes);
        // If a term exists, check its semantics
        if (t != null) {
            t.semCheck(scopes);
        }
    }

    /*
     * Function to print the Term nonterminal
     */
    void print() {
        // Print the factor
        f.print();
        // Check if there is a term to print
        if (t != null) {
            // Check if it is multiplication or division
            if (mult) {
                System.out.println("*");
            } else if (divide) {
                System.out.println("/");
            }
            // Print the term
            t.print();
        }
    }

    /*
     * Function to execute the Term nonterminal
     */
    int execute() {
        int term = 0;
        // Execute the factor
        term = f.execute();
        // Check if there is a term to execute
        if (t != null) {
            if (mult) {
                term *= t.execute();
            } else if (divide) {
                int divisor = t.execute();
                // Check for division by 0 error
                if (divisor != 0) {
                    term /= divisor;
                } else {
                    System.out.println("ERROR: Can not divide by 0!");
                    System.exit(1);
                }
            }
        }
        return term;
    }
}