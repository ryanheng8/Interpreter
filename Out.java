import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Out nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Out {
    
    // Initialize the possible child nodes
    Expr ex;

    /*
     * Function to parse the Out nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "out"
        ErrorCheck.errorCheck(S.currentToken(), Core.OUT);
        S.nextToken();

        // Checks if the token is "lparen"
        ErrorCheck.errorCheck(S.currentToken(), Core.LPAREN);
        S.nextToken();

        // Parse the expression
        ex = new Expr();
        ex.parse(S);

        // Checks if the token is "rparen"
        ErrorCheck.errorCheck(S.currentToken(), Core.RPAREN);
        S.nextToken();

        // Checks if the token is "semicolon"
        ErrorCheck.errorCheck(S.currentToken(), Core.SEMICOLON);
        S.nextToken();
    }

    /*
     * Function to check the semantics for the Term nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Check the semantics of the expression
        ex.semCheck(scopes);
    }

    /*
     * Function to print the Out nonterminal
     */
    void print() {
        System.out.println("out(");
        // Print the expression
        ex.print();
        System.out.println(");");
    }

    /*
     * Function to execute the Out nonterminal
     */
    void execute() {
        // Execute the expression
        System.out.println(ex.execute());
    }

}