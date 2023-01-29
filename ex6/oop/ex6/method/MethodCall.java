package oop.ex6.method;

import oop.ex6.errorMessage.ErrorMessage;
import oop.ex6.parser.IllegalLineException;
import oop.ex6.regex.Regex;
import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a method call in the S-Java code. It contains the name of the method,
 * the parameters given to the method, and the scope index at which the method is called.
 *
 * @author Eli Levinkopf
 */
public class MethodCall {
    public static final String OPEN_PARENTHESES = "(";
    public static final String CLOSE_PARENTHESES = ")";

    private final String methodName;
    private final List<String> methodCallParameters;
    private int scopeIndex;

    /**
     * Creates a new MethodCall object from a line of S-Java code.
     *
     * @param line the line of S-Java code representing the method call
     * @throws IllegalLineException if the method name is invalid or if the number of parameters given to the method
     *                              does not match the number of parameters in the method declaration
     */
    public MethodCall(String line) throws IllegalLineException {
        methodName = line.split(Regex.METHOD_SPLIT_REGEX)[0].trim();
        if (!methodName.matches(Regex.METHOD_NAME_REGEX)) {
            throw new IllegalLineException(String.format(ErrorMessage.INVALID_METHOD_NAME_ERROR, methodName));
        }
        methodCallParameters = new ArrayList<>();
        Arrays.stream(line.substring(line.indexOf(OPEN_PARENTHESES) + 1, line.lastIndexOf(CLOSE_PARENTHESES))
                        .split(Regex.COMMA_REGEX, -1))
                .map(String::trim)
                .filter(parameter -> !parameter.isEmpty())
                .forEach(methodCallParameters::add);

    }

    /**
     * Validates the parameters given to the method call against the parameters in the method declaration.
     *
     * @param methodDeclarationParameters the parameters in the method declaration
     * @throws IllegalLineException if the number of parameters in the method call does not match the number of
     *                              parameters in the method declaration or if the type of a parameter in the method
     *                              call does not match the type of the corresponding parameter in the method
     *                              declaration
     */
    public void validateMethodCallParameters(ArrayList<Variable> methodDeclarationParameters) throws IllegalLineException {
        if (methodCallParameters.size() != methodDeclarationParameters.size()) {
            throw new IllegalLineException(String.format(ErrorMessage.INVALID_PARAMETER_NUMBER_ERROR,
                    methodDeclarationParameters.size(), methodCallParameters.size()));
        }
        Iterator<Variable> variableIterator = methodDeclarationParameters.iterator();
        Iterator<String> stringIterator = methodCallParameters.iterator();
        while (variableIterator.hasNext() && stringIterator.hasNext()) {
            variableIterator.next().checkValueType(stringIterator.next(), scopeIndex);
        }
    }

    public int getScopeIndex() {
        return scopeIndex;
    }

    public List<String> getMethodCallParameters() {
        return methodCallParameters;
    }

    public void setScopeIndex(int scopeIndex) {
        this.scopeIndex = scopeIndex;
    }

    public String getMethodName() {
        return methodName;
    }
}