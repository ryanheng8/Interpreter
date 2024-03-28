/*
 * Class for the Function nonterminal including child nodes, parsing, executing, and printing
 */
class Func {

    // Initialize the possible child nodes
    Param p;
    StmtSeq ss;
    String id;

    /*
     * Function to parse the Function nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "procedure"
        ErrorCheck.errorCheck(S.currentToken(), Core.PROCEDURE);
        S.nextToken();

        // Checks if the token is "ID"
        ErrorCheck.errorCheck(S.currentToken(), Core.ID);
        id = S.getId();
        S.nextToken();

        // Checks if the token is "LPAREN"
        ErrorCheck.errorCheck(S.currentToken(), Core.LPAREN);
        S.nextToken();

        // Parse the Parameters
        p = new Param();
        p.parse(S);

        // Checks if the token is "RPAREN"
        ErrorCheck.errorCheck(S.currentToken(), Core.RPAREN);
        S.nextToken();

        // Checks if the token is "IS"
        ErrorCheck.errorCheck(S.currentToken(), Core.IS);
        S.nextToken();

        // Parse the Statement Sequence
        ss = new StmtSeq();
        ss.parse(S);

        // Checks if the token is "END"
        ErrorCheck.errorCheck(S.currentToken(), Core.END);
        S.nextToken();
    }

    /*
     * Function to print the Function nonterminal
     */
    void print() {
        System.out.println("procedure " + id + " (");
        // Print the parameters
        p.print();
        System.out.println(") is ");
        // Print the statement sequence
        ss.print();
        System.out.println(" end");
    }

    /*
     * Function to execute the Function nonterminal
     */
    void execute() {
        // Checks if the function name has already been taken
        if (!Memory.funcCalls.containsKey(id)) {
            // Store the function and its parameters in a map to be called later 
            Memory.funcCalls.put(id, ss);
            Memory.funcParams.put(id, p.execute());
        } else {
            System.out.println("ERROR: Function '" + id + "' is a duplicate function name!");
            System.exit(1);
        }
    }
}