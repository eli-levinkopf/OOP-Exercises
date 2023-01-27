package oop.ex6.condition;

import oop.ex6.parser.IllegalLineException;
import oop.ex6.parser.SjavaParser;
import oop.ex6.variable.Type;
import oop.ex6.variable.Variable;

import java.util.ArrayList;

public class Condition {

    private final ArrayList<String> conditionParametersList = new ArrayList<>();
    public void parsConditionLine(String line) throws IllegalLineException {
        if (!line.matches("^\\s*(while|if)\\s*\\(\\s*.*\\s*\\)\\s*\\{\\s*")) {
            throw new IllegalArgumentException("ERROR: The syntax of the condition line is incorrect.");
        }
        String conditionStatement = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
        if (conditionStatement.isBlank()){
            throw new IllegalLineException("ERROR: Illegal empty condition statement '" +line+ "'.");
        }
        String[] params = conditionStatement.split("&&|\\|\\|");
        for(String param: params){
            conditionParametersList.add(param.trim().replace(" ",""));
        }
        checkConditionParametersValidity();
    }

    private void checkConditionParametersValidity() throws IllegalLineException {
        for (String parameter : conditionParametersList){
            Type type = Type.getType(parameter);
            if (type == null){
                // searches for the variable in the most inner scope where the variable is found.
                Variable variable = SjavaParser.searchVariableInScopes(parameter);
                if (variable == null || !variable.isInitialized()){
                    throw new IllegalLineException("ERROR: The parameter used in the condition has not been decelerate or initialized.");
                }
                type = variable.getType();
            }
            if(!SjavaParser.validTypes.contains(type)) { // if type != int, double, boolean => throw exception // TODO remove this if?
                throw new IllegalLineException("ERROR: The parameter's type is not supported.");
            }
        }
    }
}