import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Loop nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Loop {

    // Initialize the possible child nodes
    Cond con;
    StmtSeq ss;

    /*
     * Function to parse the Loop nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "while"
        ErrorCheck.errorCheck(S.currentToken(), Core.WHILE);
        S.nextToken();

        // Parse the condition
        con = new Cond();
        con.parse(S);

        // Checks if the token is "do"
        ErrorCheck.errorCheck(S.currentToken(), Core.DO);
        S.nextToken();

        // Parse the statement sequence
        ss = new StmtSeq();
        ss.parse(S);

        // Checks if the token is "end"
        ErrorCheck.errorCheck(S.currentToken(), Core.END);
        S.nextToken();
    }

    /*
     * Function to check the semantics for the Loop nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check the semantics of the condition
        con.semCheck(scopes);
        // Add a new map for the scope of the loop block
        HashMap<String, Core> loop = new HashMap<String, Core>();
        scopes.push(loop);
        // Check the semantics of the statement sequence
        ss.semCheck(scopes);
        scopes.pop();
    }

    /*
     * Function to print the Loop nonterminal
     */
    void print() {
        System.out.println("while");
        // Print the condition
        con.print();
        System.out.println("do");
        // Print the statement sequence
        ss.print();
        System.out.println("end");
    }

    /*
     * Function to execute the Loop nonterminal
     */
    void execute() {
        // Adds a new local memory scope
        Memory.addScope();

        // Checks condition and executes the statement
        while (con.execute()) {
            ss.execute();
        }

        // Removes the local memory scope
        Memory.removeScope();
    }
}