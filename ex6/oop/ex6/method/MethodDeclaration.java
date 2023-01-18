package oop.ex6.method;

import oop.ex6.parser.IllegalLineException;
import oop.ex6.variable.Factory;
import oop.ex6.variable.Variable;

import java.util.ArrayList;

public class MethodDeclaration {

    private final ArrayList<Variable> methodParametersList = new ArrayList<>();

    private String name;

    public MethodDeclaration(String line) throws IllegalLineException {
        parsMethodDeclarationLine(line);
    }

    private void parsMethodDeclarationLine(String line) throws IllegalLineException {
        if (!line.matches(".*\\(.*\\)[ \\t]*\\{[ \\t]*")) { // Check if method declaration is a legal syntax.
            throw new IllegalLineException("ERROR: Invalid syntax for method declaration.");
        }
        name = line.substring(line.indexOf(" "), line.indexOf("(")).trim();
        if (!name.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) { // Check if method's name is a legal name.
            throw new IllegalLineException("ERROR: Invalid syntax for method's name.");
        }
        String parameters = line.split("[(]")[1].split("[)]")[0].trim(); // Get method's parameters.
        if (parameters.matches("^\\s*$")) { // If there is no parameters.
            return;
        }
        String[] parametersList = parameters.split("[,\\s]+");
        for (String parameter : parametersList){
            Variable newVariable = Factory.createVariable(parameter);
            if (newVariable.isInitialized()){
                throw new IllegalLineException("ERROR: The initialization of a parameter within the method definition is invalid.");
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
