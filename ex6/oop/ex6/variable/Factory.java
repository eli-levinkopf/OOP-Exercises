package oop.ex6.variable;

import oop.ex6.errorMessage.ErrorMessage;
import oop.ex6.parser.IllegalLineException;
import oop.ex6.regex.Regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The Factory class is responsible for creating variables and variable lists.
 * It provides methods to parse variable definition strings and create variable objects based on the input.
 *
 * @author Eli Levinkopf
 */
public class Factory {
    private static final Pattern pattern = Pattern.compile(Regex.VARIABLE_DEFINITION_REGEX);
    public static final String SPACE = " ";

    /**
     * Creates a Variable object based on the input string and whether it is final or not.
     *
     * @param strForVar String containing the variable definition and/or assignment
     * @param isFinal   boolean indicating whether the variable is final or not
     * @return Variable object created from the input string
     * @throws IllegalLineException if the input string does not match the expected syntax for variable
     *                              definition and/or assignment
     */
    public static Variable createVariable(String strForVar, boolean isFinal) throws IllegalLineException {
        if (strForVar.contains(Regex.ASSIGNMENT_PREFIX)) {
            return parseVariableWithAssignment(strForVar.trim(), isFinal);
        } else {
            return parseVariableWithOutAssignment(strForVar, isFinal);
        }
    }

    /**
     * Helper method to parse variable definition string without assignment.
     *
     * @param strForVar String containing the variable definition
     * @param isFinal   boolean indicating whether the variable is final or not
     * @return Variable object created from the input string
     * @throws IllegalLineException if the input string does not match the expected syntax for variable definition
     */
    private static Variable parseVariableWithOutAssignment(String strForVar, boolean isFinal) throws IllegalLineException {
        Pattern pattern = Pattern.compile(Regex.TYPE_AND_NAME_REGEX);
        Matcher matcher = pattern.matcher(strForVar);
        if (!matcher.matches()) {
            throw new IllegalLineException(String.format(ErrorMessage.INVALID_SYNTAX_FOR_VARIABLE_DECLARATION, strForVar));
        }
        Type type = Type.stringToType(matcher.group(1));
        String varName = matcher.group(2);
        return new Variable(isFinal, type, varName);
    }

    /**
     * Helper method to parse variable definition string with assignment.
     *
     * @param strForVar String containing the variable definition and assignment
     * @param isFinal   boolean indicating whether the variable is final or not
     * @return Variable object created from the input string
     * @throws IllegalLineException if the input string does not match the expected syntax for variable
     *                              definition and assignment
     */
    private static Variable parseVariableWithAssignment(String strForVar, boolean isFinal) throws IllegalLineException {
        String[] parts = strForVar.split(Regex.EQUAL_SIGN_SURROUNDED_BY_WHITESPACE_REGEX);
        Type type = Type.stringToType(parts[0].split(Regex.WHITESPACE_REGEX)[0]);
        String varName = parts[0].split(Regex.WHITESPACE_REGEX)[1];
        String value = parts[1];
        Variable newVariable = new Variable(isFinal, type, varName);
        newVariable.setInitialized(true);
        newVariable.checkValueType(value, -1); // Check if value type is machine to declared variable type.
        return newVariable;
    }

    /**
     * This method is used to create a list of variables from a given line.
     * The line must contain a valid syntax for variable declaration or assignment as defined in the class Regex.
     * If the line does not match the expected syntax, an IllegalLineException will be thrown.
     *
     * @param line the line from which the variables should be created
     * @return an ArrayList of Variable objects created from the given line
     * @throws IllegalLineException if the given line does not match the expected syntax for variable
     * declaration or assignment
     */
    public static ArrayList<Variable> createVariableList(String line) throws IllegalLineException {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalLineException(ErrorMessage.INVALID_SYNTAX_FOR_VARIABLE_DECLARATION_OR_ASSIGNMENT);
        }
        boolean isFinal = line.startsWith(Regex.FINAL_PREFIX);
        String[] parts = line.split(Regex.WHITESPACE_REGEX);
        String type = parts[isFinal ? 1 : 0];
        String variables = line.substring(line.indexOf(type) + type.length()).trim().replaceAll(Regex.SEMICOLON_PREFIX, Regex.EMPTY_STRING);
        String[] variablesStrList = variables.split(Regex.COMMA_REGEX);
        ArrayList<Variable> variablesList = new ArrayList<>();
        for (String str : variablesStrList) {
            variablesList.add(createVariable(type + SPACE + str, isFinal));
        }
        return variablesList;
    }
}
