package oop.ex6.scope;


import oop.ex6.variable.Variable;

import java.util.ArrayList;

public class Scope {

    private final ArrayList<Variable> variablesList = new ArrayList<>();

    public void add(Variable variable) {
        variablesList.add(variable);
    }
}
