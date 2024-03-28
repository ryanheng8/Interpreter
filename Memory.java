import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;

/*
 * This is the memory class containing the data structures and methods 
 * to manage variable scoping and their values
 */
class Memory {

    /*
     * This subclass contains the variable type and either the integer or array value
     */
    static class Value {
        Core type;
        int intValue;
        int[] arrayValue;
    }

    // Int to store the number of reachable arrays
    static int reachable = 0;

    // Hashmap to store global variables and a stack of stacks of maps for frames
    static HashMap<String, Value> global = new HashMap<String, Value>();
    static Stack<Stack<HashMap<String, Value>>> frames = new Stack<>();

    // Hashmap to store the function calls and parameters
    static HashMap<String, StmtSeq> funcCalls = new HashMap<String, StmtSeq>();
    static HashMap<String, ArrayList<String>> funcParams = new HashMap<String, ArrayList<String>>();

    // Initialize flag to keep track of the DeclSeq
    static boolean dsFlag = true;

    /*
     * Method to add a new frame
     */
    static void pushFrame(Stack<HashMap<String, Value>> frame) {
        frames.push(frame);
    }

    /*
     * Method to remove a frame
     */
    static void popFrame() {
        frames.pop();
    }

    /*
     * Method to add a new scope to the local variables
     */
    static void addScope() {
        frames.peek().push(new HashMap<String, Value>());
    }

    /*
     * Method to remove a scope from the local variables
     */
    static void removeScope() {
        checkReference(frames.peek().peek());
        frames.peek().pop();
    }

    /*
     * Method to declare a new integer or array
     */
    static void declare(String id, Core type) {
        // Create a new value to insert the new variable in
        Value value = new Value();
        value.type = type;

        // Check which scope to put the declaration in
        if (dsFlag) {
            global.put(id, value);
        } else {
            frames.peek().peek().put(id, value);
        }

    }

    /*
     * Method to retrieve the value of an ID
     */
    static Value getValue(String id) {
        // Declare the value to return
        Value val = null;

        // Check if the variable is global to change the value
        if (global.containsKey(id)) {
            val = global.get(id);
        } else {
            // Check if the variable is local to change the value
            for (HashMap<String, Value> ids : frames.peek()) {
                if (ids.containsKey(id)) {
                    val = ids.get(id);
                }
            }
        }
        return val;
    }

    /*
     * Method to initialize the size of the array
     */
    static void initArray(String id, int size) {
        // Initialize the aray value of the ID with +1 size to hold the reference count
        Value val = getValue(id);
        val.arrayValue = new int[size + 1];

        // Initialize the reference count to 1
        incrementCount(val);
    }

    /*
     * Method to update the value of an array with an index
     */
    static void storeIndex(String id, int index, int value) {
        // Update the array value based on the index
        getValue(id).arrayValue[index] = value;
    }

    /*
     * Method to update the value of an array with another array
     */
    static void storeArray(String id1, String id2) {
        // Get the array values
        Value val1 = getValue(id1);
        Value val2 = getValue(id2);

        // Check if there is an array to decrement the reference count
        if (val1.arrayValue != null) {
            decrementCount(val1);
        }

        // Check if there is an array to increment the reference count
        if (val2.arrayValue != null) {
            incrementCount(val2);
        }

        // Update the arrayValue from id1 to id2
        val1.arrayValue = val2.arrayValue;

    }

    /*
     * Method to update the value of an integer
     */
    static void storeInteger(String id, int value) {
        // Set the value of the ID
        getValue(id).intValue = value;
    }

    /*
     * Method to increment the reference count of an array
     */
    static void incrementCount(Value val) {
        // Checks if this array is newly reachable
        if (val.arrayValue[val.arrayValue.length - 1] == 0) {
            //Increments the reachable count and prints
            reachable++;
            System.out.println("gc:" + reachable);
        }

        // Increment the last index of the array for reference count
        val.arrayValue[val.arrayValue.length - 1] = val.arrayValue[val.arrayValue.length - 1] + 1;
    }

    /*
     * Method to decrement the reference count of an array
     */
    static void decrementCount(Value val) {
        // Decrement the last index of the array for reference count
        val.arrayValue[val.arrayValue.length - 1] = val.arrayValue[val.arrayValue.length - 1] - 1;
        
        // Checks if this array is not reachable;
        if (val.arrayValue[val.arrayValue.length - 1] == 0) {
            // Decrements the reachable count and prints
            reachable--;
            System.out.println("gc:" + reachable);
        }
    }
    
    /*
     * Method to check references to garbage collect
     */
    static void checkReference(HashMap<String, Value> scope) {
        // Loop every value in the scope being removed
        for (Value val : scope.values()) {
            // Check if the value is an array to decrement the reference count
            if (val.type == Core.ARRAY && val.arrayValue != null) {
                decrementCount(val);
            }
        }
    }
}