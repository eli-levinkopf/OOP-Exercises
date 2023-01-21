package oop.ex6.scope;


import oop.ex6.variable.Variable;

import java.util.HashMap;

public class Scope {

    private final HashMap<String, Variable> nameToVariableHashMap = new HashMap<>();

    public void add(Variable variable) {
        nameToVariableHashMap.put(variable.getName(), variable);
    }

    public HashMap<String, Variable> getNameToVariableHashMap() {
        return nameToVariableHashMap;
    }
}
