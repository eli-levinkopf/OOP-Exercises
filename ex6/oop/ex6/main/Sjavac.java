package oop.ex6.main;

import oop.ex6.parser.IllegalLineException;
import oop.ex6.parser.SjavaParser;

import java.io.FileNotFoundException;


/**
 * This class represents the main class of the program, responsible for running the program and handling exceptions.
 *
 * @author Eli Levinkopf
 */
public class Sjavac {

    public static final int FIRST_ARGUMENT = 0;
    public static final int LEGAL_CODE = 0;
    public static final int ILLEGAL_CODE = 1;
    public static final int USAGE_ERROR = 2;

    /**
     * The main method of the program, responsible for parsing the s-java file and handling any exceptions that may occur.
     *
     * @param args The command line arguments - the path to the s-java file
     */
    public static void main(String[] args) {
        try {
            checkArgument(args);
            SjavaParser.parseSjavaFile(args[FIRST_ARGUMENT]);
            System.out.println(LEGAL_CODE);
        } catch (IllegalLineException illegalLineException) {
            System.err.println(illegalLineException.getMessage());
            System.out.println(ILLEGAL_CODE);
        } catch (FileNotFoundException | IllegalUsageException err) {
            System.err.println(err.getMessage());
            System.out.println(USAGE_ERROR);
        }
    }

    /**
     * This method checks if the command line arguments are valid and throws an exception if they are not.
     *
     * @param args The command line arguments - the path to the s-java file
     * @throws IllegalUsageException if the arguments are not valid
     */
    private static void checkArgument(String[] args) throws IllegalUsageException {
        if (args.length != 1) throw new IllegalUsageException();
    }
}
