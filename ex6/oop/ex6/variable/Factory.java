package oop.ex6.variable;

import oop.ex6.parser.IllegalLineException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Factory {
    private static final Pattern pattern = Pattern.compile("(?:final\\s+)?(?:int|double|String|char|boolean)" +
            "\\s+[a-zA-Z_]\\w*(?:\\s*=\\s*[^,]+?)?(?:\\s*,\\s*[a-zA-Z_]\\w*(?:\\s*=\\s*[^,]+?)?)*;|\\w+\\s*=\\s*.+?;");
    private static Matcher matcher;

    // int i = 0, int i
    public static Variable createVariable(String strForVar, boolean isFinal) throws IllegalLineException {
        if (strForVar.contains("=")) {
            return parseVariableWithAssignment(strForVar.trim(), isFinal);
        } else {
            return parseVariableWithOutAssignment(strForVar, isFinal);
        }
    }

    private static Variable parseVariableWithOutAssignment(String strForVar, boolean isFinal) throws IllegalLineException {
        Pattern pattern = Pattern.compile("([a-zA-Z]+)\\s+([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(strForVar);
        if (!matcher.matches()) {
            throw new IllegalLineException("ERROR: Invalid syntax for variable declaration. Use the correct format:" +
                    " [Type] [Variable Name]");
        }
        Type type = Type.stringToType(matcher.group(1));
        String varName = matcher.group(2);
        return new Variable(isFinal, type, varName);
    }

    private static Variable parseVariableWithAssignment(String strForVar, boolean isFinal) throws IllegalLineException {
//        Pattern pattern = Pattern.compile( "([a-zA-Z]+)\\s+([a-zA-Z]+)\\s*=\\s*([^;]*)");
//        Matcher matcher = pattern.matcher(strForVar);
//        if (!matcher.matches()) {
//            throw new IllegalLineException("ERROR: ");
//        }
//        Type type = Type.stringToType(matcher.group(1));
//        String varName = matcher.group(2);
//        String value = matcher.group(3);
        String[] parts = strForVar.split("\\s*=\\s*");
        Type type = Type.stringToType(parts[0].split("\\s+")[0]);
        String varName = parts[0].split("\\s+")[1];
        String value = parts[1];
        Variable newVariable = new Variable(isFinal, type, varName);
//        newVariable.setValue(value);
        newVariable.setInitialized(true);
        newVariable.checkValueType(value); // Check if value type is machine to declared variable type.
        //TODO: if value == null, so Type.getType(value) == null. This is illegal?
        return newVariable;
    }

    public static ArrayList<Variable> createVariableList(String line) throws IllegalLineException {
        matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalLineException("ERROR: Invalid syntax for variable declaration or assignment.");
        }
        boolean isFinal = line.startsWith("final");
        String[] parts = line.split("\\s+");
        String type = parts[isFinal ? 1 : 0];
        String variables = line.substring(line.indexOf(type) + type.length()).trim().replaceAll(";", "");
        List<String> variablesStrList = Arrays.asList(variables.split(","));
        ArrayList<Variable> variablesList = new ArrayList<Variable>();
        for (String str : variablesStrList) {
            variablesList.add(createVariable(type + " " + str, isFinal));
        }
        return variablesList;
    }
}

class b {
    public static void main(String[] args) throws IllegalLineException {
        ArrayList<Variable> l = Factory.createVariableList("int i = 0, t = 1;");
        ArrayList<Variable> listInt = Factory.createVariableList("final int i = 5, j= -1, r, c=+2;");
        for (Variable variable : listInt) {
            System.out.println("name: " + variable.getName() + ", type: " + variable.getType() + ", isInitialized: "
                    + variable.isInitialized() + ", isFinal: " + variable.isFinal());
        }
        ArrayList<Variable> listDouble = Factory.createVariableList("final double i = 5.2, j= .1, r, c=2.;");
        for (Variable variable : listDouble) {
            System.out.println("name: " + variable.getName() + ", type: " + variable.getType() + ", isInitialized: "
                    + variable.isInitialized() + ", isFinal: " + variable.isFinal());
        }
        ArrayList<Variable> listBoolean = Factory.createVariableList("final boolean i = 5.2, j= true, r, c=1;");
        for (Variable variable : listBoolean) {
            System.out.println("name: " + variable.getName() + ", type: " + variable.getType() + ", isInitialized: "
                    + variable.isInitialized() + ", isFinal: " + variable.isFinal());
        }
        ArrayList<Variable> listString = Factory.createVariableList("final String s1 = \"some string\" , j= \"true\", r;");
        for (Variable variable : listString) {
            System.out.println("name: " + variable.getName() + ", type: " + variable.getType() + ", isInitialized: "
                    + variable.isInitialized() + ", isFinal: " + variable.isFinal());
        }
        ArrayList<Variable> listChar = Factory.createVariableList("final char i = 'w', j= 'r', r, c='x';");
        for (Variable variable : listChar) {
            System.out.println("name: " + variable.getName() + ", type: " + variable.getType() + ", isInitialized: "
                    + variable.isInitialized() + ", isFinal: " + variable.isFinal());
        }


    }
}

