package oop.ex6.variable;

import java.util.ArrayList;

public class Factory {

    public static Variable createVariable(String typeAndName){
        return new Variable();
    }

    public static ArrayList<Variable> createVariableList(String line) {
        return new ArrayList<Variable>();
    }
}
