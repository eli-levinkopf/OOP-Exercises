package oop.ex6.method;

import oop.ex6.errorMessage.ErrorMessage;
import oop.ex6.parser.IllegalLineException;
import oop.ex6.regex.Regex;
import oop.ex6.variable.VariableGenerator;
import oop.ex6.variable.Variable;

import java.util.ArrayList;

/**
 * Represents a method declaration in the s-Java code.
 * This class contains a constructor that accepts a line of code representing a method declaration,
 * and parses it to extract the method's name and parameters.
 *
 * @author Eli Levinkopf
 */
public class MethodDeclaration {

    public static final String OPEN_PARENTHESES = "(";
    private final ArrayList<Variable> methodParametersList = new ArrayList<>();
    private String name;

    /**
     * Constructor for the class.
     * Initializes the class by parsing the provided line of code for the method's name and parameters.
     *
     * @param line A line of code representing a method declaration
     * @throws IllegalLineException If the provided line of code is not a valid method declaration.
     */
    public MethodDeclaration(String line) throws IllegalLineException {
        parsMethodDeclarationLine(line);
    }

    /**
     * A private method that parses the method declaration line and extracts its name and parameters,
     * also performs validation on the syntax of the method declaration and its name
     *
     * @param line a String representing a line of code, containing the method declaration
     * @throws IllegalLineException when the syntax of the method declaration is illegal
     */
    private void parsMethodDeclarationLine(String line) throws IllegalLineException {
        if (!line.matches(Regex.METHOD_DECLARATION_SYNTAX_ERROR)) { // Check if method declaration is a legal syntax.
            throw new IllegalLineException(String.format(ErrorMessage.INVALID_SYNTAX_FOR_METHOD_DECLARATION, line));
        }
        name = line.substring(line.indexOf(Regex.SPACE_REGEX), line.indexOf(OPEN_PARENTHESES)).trim();
        if (!name.matches(Regex.LEGAL_METHOD_NAME)) { // Check if method's name is a legal name.
            throw new IllegalLineException(String.format(ErrorMessage.INVALID_SYNTAX_FOR_METHOD_NAME_ERROR, name));
        }
        // Get method's parameters.
        String parameters = line.split(Regex.OPEN_PARENTHESES_REGEX)[1].split(Regex.CLOSE_PARENTHESES_REGEX)[0].trim();
        if (parameters.matches(Regex.WHITESPACE_ONLY_REGEX)) { // If there is no parameters.
            return;
        }
        String[] parametersList = parameters.split(Regex.COMMA_REGEX);
        for (String parameter : parametersList){
            Variable newVariable = VariableGenerator.createVariable(parameter.trim(), false);
            if (newVariable.isInitialized()){
                throw new IllegalLineException(ErrorMessage.INVALID_PARAMETER_INITIALIZATION_IN_METHOD_DEFINITION);
            }
            methodParametersList.add(newVariable);
        }
    }

    public ArrayList<Variable> getMethodParametersList() {
        return methodParametersList;
    }

    public String getName() {
        return name;
    }
}
