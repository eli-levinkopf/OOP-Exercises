package oop.ex6.condition;

import oop.ex6.errorMessage.ErrorMessage;
import oop.ex6.parser.IllegalLineException;
import oop.ex6.parser.SjavaParser;
import oop.ex6.regex.Regex;
import oop.ex6.variable.Type;
import oop.ex6.variable.Variable;

import java.util.ArrayList;

/**
 * This class represents a condition in the Sjava code. It contains methods for parsing and validating
 * a condition line.
 * @author Eli Levinkopf
 */
public class Condition {

    public static final String OPEN_PARENTHESES = "(";
    public static final String CLOSE_PARENTHESES = ")";

    private final ArrayList<String> conditionParametersList = new ArrayList<>();


    /**
     * Parses a line of code that represents a condition in the S-Java language.
     *
     * @param line a string representing the line of code to be parsed
     * @throws IllegalLineException if the line of code does not match the correct syntax for a condition line
     * or if the condition statement is blank or if any of the parameters are invalid.
     */
    public void parsConditionLine(String line) throws IllegalLineException {
        if (!line.matches(Regex.CONDITION_LINE_SYNTAX)) {
            throw new IllegalArgumentException(String.format(ErrorMessage.INCORRECT_CONDITION_LINE_SYNTAX_ERROR, line));
        }
        String conditionStatement = line.substring(line.indexOf(OPEN_PARENTHESES) + 1, line.lastIndexOf(CLOSE_PARENTHESES));
        if (conditionStatement.isBlank()) {
            throw new IllegalLineException(String.format(ErrorMessage.EMPTY_CONDITION_ERROR, line));
        }
        String[] params = conditionStatement.split(Regex.CONDITION_PARAMS);
        for (String param : params) {
            conditionParametersList.add(param.trim().replace(Regex.SPACE_REGEX, Regex.EMPTY_STRING));
        }
        checkConditionParametersValidity();
    }

    /**
     * Check the validity of the parameters extracted from the condition line by the parsConditionLine method.
     *
     * @throws IllegalLineException if any of the parameters are invalid
     */
    private void checkConditionParametersValidity() throws IllegalLineException {
        for (String parameter : conditionParametersList) {
            Type type = Type.getType(parameter);
            if (type == null) {
                // searches for the variable in the most inner scope where the variable is found.
                Variable variable = SjavaParser.searchVariableInScopes(parameter);
                if (variable == null || !variable.isInitialized()) {
                    throw new IllegalLineException(String.format(ErrorMessage.VARIABLE_NOT_INITIALIZED_OR_DECLARED_ERROR
                            , parameter));
                }
                type = variable.getType();
            }
            if (!SjavaParser.validTypes.contains(type)) { // if type != int, double, boolean => throw exception
                throw new IllegalLineException(String.format(ErrorMessage.UNSUPPORTED_PARAMETER_TYPE_ERROR, type));
            }
        }
    }
}