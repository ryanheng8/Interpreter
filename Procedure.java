import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Procedure nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Procedure {

    // Initialize the possible child nodes
    String id;
    DeclSeq ds;
    StmtSeq ss;

    /*
     * Function to parse the Procedure nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "procedure"
        ErrorCheck.errorCheck(S.currentToken(), Core.PROCEDURE);
        S.nextToken();

        // Checks if the token is "ID"
        ErrorCheck.errorCheck(S.currentToken(), Core.ID);
        id = S.getId();
        S.nextToken();

        // Checks if the token is "is"
        ErrorCheck.errorCheck(S.currentToken(), Core.IS);
        S.nextToken();

        // Checks if the token is "begin", if not must be DeclSeq to parse
        if (S.currentToken() != Core.BEGIN) {
            ds = new DeclSeq();
            ds.parse(S);
        }

        // Checks if the token is "begin"
        ErrorCheck.errorCheck(S.currentToken(), Core.BEGIN);
        S.nextToken();

        // Parse the StmtSeq
        ss = new StmtSeq();
        ss.parse(S);

        // Checks if the token is "end"
        ErrorCheck.errorCheck(S.currentToken(), Core.END);
        S.nextToken();

        // Checks if the token is "EOS"
        ErrorCheck.errorCheck(S.currentToken(), Core.EOS);
        S.nextToken();
    }

    /*
     * Function to check the semantics for the Procedure nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check if there is a DeclSeq to semantically check
        if (ds != null) {
            // Add a new map for global variable scope
            HashMap<String, Core> global = new HashMap<String, Core>();
            scopes.push(global);
            ds.semCheck(scopes);
        }
        // Add a new map for the scope of the main statement sequence
        HashMap<String, Core> mainSs = new HashMap<String, Core>();
        scopes.push(mainSs);
        ss.semCheck(scopes);
        scopes.pop();
    }

    /*
     * Function to print the Procedure nonterminal
     */
    void print() {
        System.out.println("procedure " + id + " is ");
        // Check if there is a DeclSeq to print
        if (ds != null) {
            // Print the DeclSeq
            ds.print();
        }
        System.out.println("begin");
        // Print the StmtSeq
        ss.print();
        System.out.println("end");
    }

    /*
     * Function to execute the Procedure nonterminal
     */
    void execute() {

        // Check if there is a DeclSeq to execute
        if (ds != null) {
            // Execute the DeclSeq
            ds.execute();
        }
        // Default is set to true, changes to false after potential DeclSeq
        Memory.dsFlag = false;

        // Add a new frame for the main function
        Memory.pushFrame(new Stack<HashMap<String, Memory.Value>>());

        // Add a new scope for the SS
        Memory.addScope();

        // Execute the StmtSeq
        ss.execute();

        // Remove the scope for the end of the program
        Memory.removeScope();

        // Remove the frame for the end of the program
        Memory.popFrame();

        // Clean up global variables
        Memory.checkReference(Memory.global);
    }
}