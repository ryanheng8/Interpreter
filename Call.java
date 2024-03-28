import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * Class for the Call nonterminal including child nodes, parsing, executing, and printing
 */
class Call {

    // Initialize the possible child nodes
    Param p;
    String id;

    /*
     * Function to parse the Call nonterminal
     */
    void parse(Scanner S) {
        // Checks if the token is "begin"
        ErrorCheck.errorCheck(S.currentToken(), Core.BEGIN);
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

        // Checks if the token is "SEMICOLON"
        ErrorCheck.errorCheck(S.currentToken(), Core.SEMICOLON);
        S.nextToken();
    }

    /*
     * Function to print the Call nonterminal
     */
    void print() {
        System.out.println("begin " + id + " (");
        p.print();
        System.out.println(");");
    }

    /*
     * Function to execute the Call nonterminal
     */
    void execute() {
        if (Memory.funcCalls.containsKey(id)) {
            // Initialize the frame to add to the stack
            Stack<HashMap<String, Memory.Value>> frame = new Stack<HashMap<String, Memory.Value>>();
            frame.add(new HashMap<String, Memory.Value>());

            // Gather the formal parameters
            ArrayList<String> formalParams = Memory.funcParams.get(id);

            // Check that the formal parameters are distinct
            HashSet<String> dupCheck = new HashSet<String>();
            for (String id : formalParams) {
                if (!dupCheck.contains(id)) {
                    dupCheck.add(id);
                } else {
                    System.out.println("ERROR: The formal parameters of function '" + id + "' are not distinct!");
                    System.exit(1);
                }
            }

            // Gather the actual parameters
            ArrayList<String> actualParams = p.execute();

            // Add the formal parameters in the new frame
            for (int i = 0; i < formalParams.size(); i++) {
                // Initialize the formal parameters
                frame.peek().put(formalParams.get(i), new Memory.Value());
                frame.peek().get(formalParams.get(i)).type = Core.ARRAY;

                // Get the actual parameters
                Memory.Value var = Memory.getValue(actualParams.get(i));
                if (var.type == Core.ARRAY) {
                    frame.peek().get(formalParams.get(i)).arrayValue = var.arrayValue;
                    Memory.incrementCount(var);
                } else {
                    System.out.println("ERROR: '" + actualParams.get(i) + "' is not an array and could not be passed to functions!");
                    System.exit(1);
                }
            }

            // Push on the new frame
            Memory.pushFrame(frame);

            // Execute the function
            Memory.funcCalls.get(id).execute();

            // Remove the scope
            Memory.removeScope();

            // Remove the frame
            Memory.popFrame();
        } else {
            System.out.println("ERROR: Function '" + id + "' has not been declared!");
            System.exit(1);
        }
    }
}