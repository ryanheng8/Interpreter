import java.util.ArrayList;

/*
 * Class for the Parameter nonterminal including child nodes, parsing, executing, and printing
 */
class Param {

    // Initialize the possible child nodes
    String id;
    Param p;

    /*
     * Function to parse the Parameter nonterminal
     */
    void parse(Scanner S) {

        // Checks if the token is "ID"
        ErrorCheck.errorCheck(S.currentToken(), Core.ID);
        id = S.getId();
        S.nextToken();

        // Checks if there is another parameter to parse
        if (S.currentToken() == Core.COMMA) {
            // Consume the comma
            S.nextToken();
            
            // Parse the next parameter
            p = new Param();
            p.parse(S);
        }
    }

    /*
     * Function to print the Parameter nonterminal
     */
    void print() {
        // Checks which factor option to print
        System.out.println(id);
        if (p != null) {
            System.out.println(", ");
            p.print();
        }
    } 

    /*
     * Function to execute the Factor nonterminal
     */
    ArrayList<String> execute() {
        // Initialize the arraylist to return
        ArrayList<String> parameters = new ArrayList<String>();
        
        // Adds the parameter to the list
        parameters.add(id);

        // Recursively combines the arraylists
        if (p != null) {
            parameters.addAll(p.execute());
        }

        return parameters;
    } 
}