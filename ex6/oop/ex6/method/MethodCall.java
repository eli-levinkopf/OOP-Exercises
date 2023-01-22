package oop.ex6.method;

import oop.ex6.parser.IllegalLineException;
import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class MethodCall {

    public static final String METHOD_NAME_REGEX = "([a-zA-Z][a-zA-Z0-9_]*)\\s*";
    private final String methodName;
    private static final List<String> methodCallParameters = new ArrayList<>();

    public MethodCall(String line) throws IllegalLineException {
        methodName= line.split("\\(")[0].trim();
        if (!methodName.matches(METHOD_NAME_REGEX)){
            throw new IllegalLineException("ERROR: Invalid method call: '" + methodName + "'.\nThe method name must start with a letter" +
                    " and can only contain letters, numbers, and underscores.");
        }
        Arrays.stream(line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).split(",", -1))
                .map(String::trim)
                .forEach(methodCallParameters::add);
    }

    public static void validateMethodCallParameters(ArrayList<Variable> methodDeclarationParameters) throws IllegalLineException{
        if (methodCallParameters.size() != methodDeclarationParameters.size()){
            throw new IllegalLineException("ERROR: Number of parameters passed in method call does not match the " +
                    "number of parameters in method declaration." +  " Expected "
                    + methodDeclarationParameters.size() + " but got " + methodCallParameters.size() + ".");
        }
        IntStream.range(0, methodDeclarationParameters.size())
                .forEach(i -> {
                    try {
                        methodDeclarationParameters.get(i).checkValueType(methodCallParameters.get(i));
                    } catch (IllegalLineException e) {
                        throw new RuntimeException(e);
                    }
                });
//        Iterator<Variable> variableIterator = methodDeclarationParameters.iterator();
//        Iterator<String> stringIterator = methodCallParameters.iterator();
//        while (variableIterator.hasNext() && stringIterator.hasNext()){
//            variableIterator.next().checkValueType((stringIterator.next()));
//        }
    }

    public String getMethodName() {
        return methodName;
    }
}