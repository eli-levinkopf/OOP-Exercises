package oop.ex6.main;

import oop.ex6.errorMessage.ErrorMessage;

/**
 * This class represents the exception thrown when the program is used in an illegal way.
 *
 * @author Eli Levinkopf
 */
public class IllegalUsageException extends Exception {

    /**
     * This constructor creates a new instance of the IllegalUsageException class.
     */
    public IllegalUsageException() {
        super(ErrorMessage.ILLEGAL_USAGE_ERROR);
    }
}
