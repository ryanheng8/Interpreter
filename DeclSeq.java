import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Declaration Sequence nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class DeclSeq {

    // Initialize the possible child nodes
    Decl d;
    DeclSeq ds;
    Func f;

    /*
     * Function to parse the Declaration Sequence nonterminal
     */
    void parse(Scanner S) {
        if (S.currentToken() == Core.PROCEDURE) {
            // Parse the Func
            f = new Func();
            f.parse(S);
        } else if (S.currentToken() == Core.INTEGER || S.currentToken() == Core.ARRAY) {
            // Parse the Decl
            d = new Decl();
            d.parse(S);
        }

        // Checks if there is a DeclSeq to parse
        if (S.currentToken() == Core.INTEGER || S.currentToken() == Core.ARRAY || S.currentToken() == Core.PROCEDURE) {
            ds = new DeclSeq();
            ds.parse(S);
        }
    }

    /*
     * Function to check the semantics for the Declaration Sequence nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Semantically check the declaration
        d.semCheck(scopes);
        // Check if there is a declaration sequence to semantically check
        if (ds != null) {
            ds.semCheck(scopes);
        }
    }

    /*
     * Function to print the Declaration Sequence nonterminal
     */
    void print() {
        // Print the declaration or function
        if (d != null) {
            d.print();
        } else if (f != null) {
            f.print();
        }
        // Check if there is a declaration sequence to print
        if (ds != null) {
            ds.print();
        }
    }

    /*
     * Function to execute the Declaration Sequence nonterminal
     */
    void execute() {
        // Execute the declaration or function
        if (d != null) {
            d.execute();
        } else if (f != null) {
            f.execute();
        }
        // Check if there is a declaration sequence to execute
        if (ds != null) {
            ds.execute();
        }
    }
}