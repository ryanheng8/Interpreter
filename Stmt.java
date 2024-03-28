import java.util.HashMap;
import java.util.Stack;

/*
 * Class for the Statement nonterminal including child nodes, parsing, semantic checking, executing, and printing
 */
class Stmt {

    // Initialize the possible child nodes
    Assgn a;
    If i;
    Loop l;
    Out o;
    In in;
    Decl d;
    Call c;

    /*
     * Function to parse the Statement nonterminal
     */
    void parse(Scanner S) {

        // Check for the possible statement options to parse
        if (S.currentToken() == Core.ID) {
            // Parse assgn
            a = new Assgn();
            a.parse(S);
         } else if (S.currentToken() == Core.IF) {
            // Parse if
            i = new If();
            i.parse(S);
         } else if (S.currentToken() == Core.WHILE) {
            // Parse loop
            l = new Loop();
            l.parse(S);
         } else if (S.currentToken() == Core.OUT) {
            // Parse out
            o = new Out();
            o.parse(S);
         } else if (S.currentToken() == Core.IN) {
            // Parse in
            in = new In();
            in.parse(S);
         } else if ((S.currentToken() == Core.ARRAY) || (S.currentToken() == Core.INTEGER)) {
            // Parse decl
            d = new Decl();
            d.parse(S);
         } else if (S.currentToken() == Core.BEGIN) {
            // Parse the call
            c = new Call();
            c.parse(S);
        } else {
            System.out.println("ERROR: Expecting a token for statement (ID, IF, WHILE, OUT, IN, ARRAY, INTEGER, or BEGIN), but recieved '" + S.currentToken() + "'!");
            System.exit(1);
        }
    }

    /*
     * Function to check the semantics for the Statement nonterminal
     */
    void semCheck(Stack<HashMap<String, Core>> scopes) {
        // Checks which statement option to semantically check
        if (a != null) {
            a.semCheck(scopes);
        } else if (i != null) {
            i.semCheck(scopes);
        } else if (l != null) {
            l.semCheck(scopes);
        } else if (o != null) {
            o.semCheck(scopes);
        } else if (in != null) {
            in.semCheck(scopes);
        } else if (d != null) {
            d.semCheck(scopes);
        }
    }

    /*
     * Function to print the Statement nonterminal
     */
    void print() {
        // Checks which statement option to print
        if (a != null) {
            a.print();
        } else if (i != null) {
            i.print();
        } else if (l != null) {
            l.print();
        } else if (o != null) {
            o.print();
        } else if (in != null) {
            in.print();
        } else if (d != null) {
            d.print();
        } else if (c != null) {
            c.print();
        }
    }

    /*
     * Function to execute the Statement nonterminal
     */
    void execute() {
        // Checks which statement option to execute
        if (a != null) {
            a.execute();
        } else if (i != null) {
            i.execute();
        } else if (l != null) {
            l.execute();
        } else if (o != null) {
            o.execute();
        } else if (in != null) {
            in.execute();
        } else if (d != null) {
            d.execute();
        } else if ( c!= null) {
            c.execute();
        }
    }
}