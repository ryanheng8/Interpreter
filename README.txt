Name: Ryan Heng

Files:
    Assgn.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the assign nonterminal.
    Call.java:
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the call nonterminal.
    Compr.java:
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the compare nonterminal.
    Cond.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the codition nonterminal.
    Core.java: 
        This file contains all of the enums for the tokens in the Core language.
    Decl.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the declaration nonterminal.
    DeclArr.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the declaration array nonterminal.
    DeclInt.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the declaration integer nonterminal.
    DeclSeq.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the declaration sequence nonterminal.
    ErrorCheck.java:
        This file contains the function to compare the test token to the expected token to check for
        and error to print and exit.
    Expr.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the expression nonterminal.
    Factor.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the factor nonterminal.
    Func.java:
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the function nonterminal.
    If.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the if nonterminal.
    In.java: This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the in nonterminal.
    Loop.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the loop nonterminal.
    Main.java: 
        This file contains the main method to instantiate the scanner, parse, semantically check, print, and execute 
        the parse tree for the input file and data file.
    Memory.java:
        This file contains the data structures to hold the variable scopes as well as the methods to adjust the scopes 
        and change the values of the variables. It also contains the subclass Value to hold the variable type and values.
        This file also contains the methods to keep track of the array reference counts.
    Out.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the out nonterminal.
    Param.java:
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the param nonterminal.
    Procedure.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the procedure nonterminal.
    Scanner.java: 
        This file contains the Scanner that reads the file as well as methods to call the tokens and get the 
        identifier or constant values.
    Stmt.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the statement nonterminal.
    StmtSeq.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the statement sequence nonterminal.
    Term.java: 
        This file contains the functions to parse, semantically check, print, and execute the parse tree
        for the term nonterminal.

Special Features or Comments:
    The memory class, value subclass, and variable scope management were designed using the suggested method of using
    a stack of stacks of maps for the frames. The reference counting is done by adding an additional position to each 
    array to hold the count.

Testing Procedure and Known Bugs:
    I tested the interpreter by creating test cases for the edge cases as well as testing error inputs that should
    result in each error message being printed.

    No known bugs.
    