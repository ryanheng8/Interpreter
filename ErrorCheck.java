/*
 * This class is used to check for errors with matching Core tokens
 */
class ErrorCheck {
    // This function takes in a test and expected token to see if they match
    public static void errorCheck (Core testToken, Core expectedToken) {
        // Checks if the tokens match, if not print error and exit
        if (testToken != expectedToken) {
            System.out.println("ERROR: Expecting '" + expectedToken + "', but recieved '" + testToken + "'!");
            System.exit(1);
        } 
    }
}