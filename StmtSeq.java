import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Statement Sequence nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class StmtSeq {

    // Initialize the possible child nodes
    Stmt s;
    StmtSeq ss;

    /*
     * Function to parse the Statement Sequence nonterminal
     */
    void parse(Scanner S) {
        // Parse the statement
        s = new Stmt();
        s.parse(S);

        // Check if there is a StmtSeq to parse
        if ((S.currentToken() != Core.ELSE) && (S.currentToken() != Core.END) ) {
            // Parse the statement sequence
            ss = new StmtSeq();
            ss.parse(S);
        }
    }

    /*
     * Function to check the semantics for the Statement Sequence nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check the statement semantics
        s.semCheck(scopes);
        // If a statement sequence exists, check its semantics
        if (ss != null) {
            ss.semCheck(scopes);
        }
    }

    /*
     * Function to print the Statement Sequence nonterminal
     */
    void print() {
        // Print the statement
        s.print();
        // Check if a statement sequence exists to print
        if (ss != null) {
            ss.print();
        }
    }

    /*
     * Function to execute the Statement Sequence nonterminal
     */
    void execute() {
        // Execute the statement
        s.execute();
        // Check if a statement sequence exists to execute
        if (ss != null) {
            ss.execute();
        }
    }
}