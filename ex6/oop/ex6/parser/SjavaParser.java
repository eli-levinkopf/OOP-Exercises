package oop.ex6.parser;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import oop.ex6.condition.Condition;
import oop.ex6.method.MethodCall;
import oop.ex6.method.MethodDeclaration;
import oop.ex6.scope.Scope;
import oop.ex6.variable.Factory;
import oop.ex6.variable.Type;
import oop.ex6.variable.Variable;

public class SjavaParser {
    public static final Set<Type> validTypes = EnumSet.of(Type.BOOLEAN, Type.DOUBLE, Type.INT);

    public static final String COMMENT_REGEX = "^\\s*//.*";
    public static final String WHITE_SPACE_CHECKER_REGEX = ".*\\s.*";
    public static final String DATA_TAPES_REGEX = "(int|double|String|char|boolean).*";
    public static final String METHOD_CALL_REGEX = "^.+\\(.*\\)\\s*;";
    public static final String IF_WHILE_REGEX = "^(if|while).*";
    public static final String VARIABLE_DECLARATION_REGEX = "^(final)?\\s*(int|double|String|char|boolean)\\s.*";
    public static final String SPACE_REGEX = " ";
    public static final String VOID_PREFIX = "void";
    public static final String RETURN_PREFIX = "return";
    public static final String FINAL_PREFIX = "final";
    public static final String FILE_NOT_FOUND_ERROR_MSG = "File not found";
    public static final String COMMENT_PREFIX = "//";
    public static final String ILLEGAL_COMMENT_LINE_ERROR_MSG = "Illegal comment line." +
            " Comment line most to start with '//'.";
    public static final String CLOSED_AFTER_RETURN_ERROR_MSG = "Error: Scope not closed after return statement." +
            " All scopes must be closed after a return statement";
    public static final String INVALID_SCOPES_SYNTAX = "ERROR: Invalid scope closing syntax";
    public static final String SYNTAX_ERROR = "ERROR: Syntax error";
    public static final String UNTERMINATED_GLOBAL_SCOPE = "ERROR: The code must be terminated with a '}' " +
            "in the global scope to indicate the end of the program.";
    private static final HashMap<String, ArrayList<Variable>> methodSignatureToVariablesMap = new HashMap<>();
    private static final ArrayList<MethodCall> methodCallsList = new ArrayList<>();
    private static final LinkedList<Scope> scopesList = new LinkedList<>();
    private static boolean returnWithOutCloseScope = false;


    public static void parseSjavaFile(String pathToSjavaFile) throws IllegalLineException, FileNotFoundException {
        try {
            File sjavaFile = new File(pathToSjavaFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sjavaFile));
            String line;
            scopesList.add(new Scope()); // creates a new scope for global scope and adds it to scopes list.
            while ((line = bufferedReader.readLine()) != null) {
                validateLineSyntax(line);
            }
            //TODO: validateMethodCalls() //checks validity for all method calls
            if (scopesList.size() != 1) { // Check if the code ends with a '{' character.
                throw new IllegalLineException(UNTERMINATED_GLOBAL_SCOPE);
            }
        } catch (IllegalLineException illegalLineException) {
            throw new IllegalStateException(illegalLineException.getMessage());
        } catch (IOException ioException) {
            throw new FileNotFoundException(FILE_NOT_FOUND_ERROR_MSG);
        }

    }

    private static void validateLineSyntax(String line) throws IllegalLineException {
        if (lineIsIgnored(line) || validateScopeClosure(line)) {
            return;
        }
        if (returnWithOutCloseScope) {
            throw new IllegalStateException(CLOSED_AFTER_RETURN_ERROR_MSG);
        }
        line = line.trim(); // remove spaces from the start and end of line.
        if (scopesList.size() == 1) { // Line in global scope.
            checkLineValidityInGlobalScope(line); // check code in global scope
        } else if (scopesList.size() > 1) { // Line in method scope.
            checkLineValidityInMethodScope(line); // check code in method scope
        } else {
            throw new IllegalStateException(SYNTAX_ERROR);
        }

    }

    private static void checkLineValidityInMethodScope(String line) throws IllegalLineException {
        if (line.startsWith(RETURN_PREFIX)) { // Check if line starts with a return statement.
            returnWithOutCloseScope = true;
        } else if (line.matches(METHOD_CALL_REGEX)) { // Check if line starts is a method call.
            methodCallsList.add(new MethodCall(line)); // Create a new method call and add it to the methodCallList.
        } else if (line.matches(IF_WHILE_REGEX)) { // Check if/While statement
            scopesList.add(new Scope()); // Adds condition's scope to the scopes list.
            Condition.parsConditionLine(line);
        } else if (line.matches(VARIABLE_DECLARATION_REGEX)) { // Check variable declaration
            checkLineValidityForVariableDeclaration(line);
        } else { // Check variable assignment
            checkVariableAssignmentValidity(line);
        }
    }

    private static void checkVariableAssignmentValidity(String line) throws IllegalLineException {
        if (!line.endsWith(";")) { //TODO: remove this check?
            throw new IllegalLineException("ERROR: ");
        }
        String[] parts = line.split("=");
        String variableName = parts[0].trim();
        String value = parts[1].trim();
        Variable variable = SjavaParser.searchVariableInScopes(variableName);
        if (variable == null || variable.isFinal()) {
            throw new IllegalLineException("ERROR: The variable '" + variableName + "' is either not defined" +
                    " or is a final variable, therefore it cannot be reassigned.");
        }
        variable.checkValueType(value); // Check if value type is machine to declared variable type.
        variable.setInitialized(true);
    }


    private static void checkLineValidityInGlobalScope(String line) throws IllegalLineException {
        if (!line.matches(WHITE_SPACE_CHECKER_REGEX)) { // If line like "int", throw exception.
            throw new IllegalLineException(SYNTAX_ERROR);
        }
        if (line.startsWith(VOID_PREFIX)) {
            checkLineValidityForMethodDeclaration(line); // Check if method declaration is valid.
        } else if (line.startsWith(FINAL_PREFIX) ||
                line.split(SPACE_REGEX)[0].matches(DATA_TAPES_REGEX)) {
            checkLineValidityForVariableDeclaration(line); // Check if validity declaration is valid.
        } else {
            throw new IllegalLineException(SYNTAX_ERROR);
        }
    }

    private static void checkLineValidityForVariableDeclaration(String line) throws IllegalLineException {
        ArrayList<Variable> newVariableList = Factory.createVariableList(line); // Line can contain more than one variable declaration
        if (newVariableList.size() == 0) {
            throw new IllegalLineException("ERROR: The syntax used for declaring the variable is incorrect.");
        }
        for (Variable variable : newVariableList) {
            if (variable.isFinal() && !variable.isInitialized()) {
                throw new IllegalLineException("ERROR: Cannot declare a final variable without initializing it " +
                        "(variable name: " + variable.getName() + ").");
            }
            scopesList.getLast().add(variable); // Add the variable to the current scope.
        }
    }

    private static void checkLineValidityForMethodDeclaration(String line) throws IllegalLineException {
        MethodDeclaration methodDeclaration = new MethodDeclaration(line);
        // Check if the method's name already exists
        if (methodSignatureToVariablesMap.containsKey(methodDeclaration.getName())) {
            throw new IllegalLineException("ERROR: A method with the same name already exists.");
        }
        methodSignatureToVariablesMap.put(methodDeclaration.getName(), methodDeclaration.getMethodParametersList());
        scopesList.add(new Scope()); // Open a new scope.
        for (Variable variable : methodDeclaration.getMethodParametersList()) { // Add all variables to the scope.
            scopesList.getLast().add(variable);
        }
    }

    private static boolean validateScopeClosure(String line) throws IllegalStateException {
        if (Pattern.matches("\\s*}\\s*", line)) {
            if (scopesList.size() <= 1) {
                throw new IllegalStateException(INVALID_SCOPES_SYNTAX);
            }
            scopesList.removeLast(); // Closings the least scope.
            returnWithOutCloseScope = false;
            return true;
        }
        return false;
    }

    private static boolean lineIsIgnored(String line) throws IllegalLineException {
        isIllegalCommentLine(line);
        return line.isEmpty() || line.startsWith(COMMENT_PREFIX);
    }

    private static void isIllegalCommentLine(String line) throws IllegalLineException {
        if (Pattern.matches(COMMENT_REGEX, line) && !line.startsWith(COMMENT_PREFIX)) {
            throw new IllegalLineException(ILLEGAL_COMMENT_LINE_ERROR_MSG);
        }
    }


    public static Variable searchVariableInScopes(String parameterName) {
        return scopesList.stream().map(Scope::getNameToVariableHashMap)
                .map(map -> map.get(parameterName))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }


//    public static Variable searchVariableInScopes(String parameterName) {
//        for (int i = scopesList.size() - 1; i >= 0; i--) {
//            Variable variable = scopesList.get(i).getNameToVariableHashMap().get(parameterName);
//            if (variable != null) {
//                return variable;
//            }
//        }
//        return null;
//    }

//    public static Variable searchVariableInScopes(String parameterName) {
//        for (Scope scope : scopesList) {
//            Variable variable = scope.getNameToVariableHashMap().get(parameterName);
//            if (variable != null) {
//                return variable;
//            }
//        }
//        return null;
//    }
}

