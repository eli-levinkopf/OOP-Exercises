package oop.ex6.parser;

/**
 * This class represents an exception that is thrown when an illegal line is encountered during parsing.
 *
 * @author Eli Levinkopf
 */
public class IllegalLineException extends Exception {

    /**
     * Constructs a new exception with the specified error message.
     *
     * @param message the error message
     */
    public IllegalLineException(String message) {
        super(message);
    }
}
