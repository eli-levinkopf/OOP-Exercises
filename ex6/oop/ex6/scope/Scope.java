package oop.ex6.scope;


import oop.ex6.variable.Variable;

import java.util.HashMap;


/**
 * Represents a scope in a Sjava program, containing a collection of variables and their associated names.
 */
public class Scope {

    /**
     * HashMap containing the names of the variables as keys and the variables themselves as values.
     */
    private final HashMap<String, Variable> nameToVariableHashMap = new HashMap<>();

    /**
     * Adds a variable to the scope.
     *
     * @param variable the variable to add to the scope.
     */
    public void add(Variable variable) {
        nameToVariableHashMap.put(variable.getName(), variable);
    }

    /**
     * @return the HashMap containing the names of the variables as keys and the variables themselves as values.
     */
    public HashMap<String, Variable> getNameToVariableHashMap() {
        return nameToVariableHashMap;
    }
}
