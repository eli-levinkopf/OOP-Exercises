package oop.ex6.parser;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.condition.Condition;
import oop.ex6.errorMessage.ErrorMessage;
import oop.ex6.method.MethodCall;
import oop.ex6.method.MethodDeclaration;
import oop.ex6.regex.Regex;
import oop.ex6.scope.Scope;
import oop.ex6.variable.Factory;
import oop.ex6.variable.Type;
import oop.ex6.variable.Variable;


/**
 * The class SjavaParser is a parser for the Sjava programming language. It has a parseSjavaFile method that takes
 * the path to a Sjava file, reads the file line by line and validates each line for syntax. The line is trimmed and
 * checked for ignoring or scope closure. If the line is in the global scope, the line validity is checked in the global
 * scope and if it is in the method scope, line validity is checked in the method scope. If the line contains a return
 * statement without closing a scope, an error is thrown. If the code ends with an unterminated scope, an
 * error is thrown. The method calls are also checked for validity and if any method call is not defined, an error
 * is thrown.
 * The class has several instance variables such as methodSignatureToVariablesMap, methodCallsCollection,
 * scopesCollection, scopesCache, returnWithoutCloseScope, and endWithReturnStatement. It also has a setDataStructures
 * method to set or clear the data structures.
 *
 * @author Eli Levinkopf
 */
public class SjavaParser {

    /* A set that contains the valid types that are supported in the Sjava language. */
    public static final Set<Type> validTypes = EnumSet.of(Type.BOOLEAN, Type.DOUBLE, Type.INT);


    /* A HashMap that maps the names of methods to a list of variables associated with them. */
    private static HashMap<String, ArrayList<Variable>> methodSignatureToVariablesMap;

    /* A list of MethodCall objects. */
    private static ArrayList<MethodCall> methodCallsCollection;

    /* A linked list of Scope objects, representing all the scopes in the code. */
    private static LinkedList<Scope> scopesCollection;

    /* A cache of all the scopes in the code, used to keep track of scopes during parsing. */
    private static ArrayList<Scope> scopesCache;

    /* A boolean flag indicating whether a return statement has been encountered without a scope closure. */
    private static boolean returnWithoutCloseScope = false;

    /* A boolean flag indicating whether the code ends with a return statement. */
    private static boolean endWithReturnStatement = true;


    /**
     * The main function that parses a Sjava file.
     *
     * @param pathToSjavaFile The file path of the Sjava file to parse.
     * @throws IllegalLineException  if the code contains a syntax error or if the file is not found.
     * @throws FileNotFoundException if the Sjava file is not found.
     */
    public static void parseSjavaFile(String pathToSjavaFile) throws IllegalLineException, FileNotFoundException {
        try {
            File sjavaFile = new File(pathToSjavaFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sjavaFile));
            String line;
            setDataStructures(true);
            scopesCollection.add(new Scope()); // creates a new scope for global scope and adds it to scopes list.
            scopesCache.add(new Scope()); // creates a new scope for
            while ((line = bufferedReader.readLine()) != null) {
                validateLineSyntax(line);
            }
            if (scopesCollection.size() != 1) { // Check if the code ends with a '{' character.
                throw new IllegalLineException(ErrorMessage.UNTERMINATED_GLOBAL_SCOPE_MSG);
            }
            validateMethodCalls(); //checks validity for all method calls
            setDataStructures(false);
        } catch (IllegalLineException illegalLineException) {
            throw new IllegalLineException(illegalLineException.getMessage());
        } catch (IOException ioException) {
            throw new FileNotFoundException(ErrorMessage.FILE_NOT_FOUND_ERROR_MSG);
        }

    }

    /**
     * Initializes or resets the data structures used in the parsing process.
     *
     * @param isStart A boolean flag indicating whether the data structures should be initialized or reset.
     */
    private static void setDataStructures(boolean isStart) {
        if (isStart) {
            returnWithoutCloseScope = false;
            endWithReturnStatement = true;
            methodSignatureToVariablesMap = new HashMap<>();
            scopesCollection = new LinkedList<>();
            methodCallsCollection = new ArrayList<>();
            scopesCache = new ArrayList<>();
        } else {
            methodSignatureToVariablesMap = null;
            methodCallsCollection = null;
            scopesCollection = null;
            scopesCache = null;
        }
    }


    /**
     * Validates all the method calls in the code.
     *
     * @throws IllegalLineException if a method call is not valid.
     */
    private static void validateMethodCalls() throws IllegalLineException {
        if (methodCallsCollection.isEmpty()) {
            return; // no method calls found.
        }
        for (MethodCall methodCall : methodCallsCollection) {
            if (!methodSignatureToVariablesMap.containsKey(methodCall.getMethodName())) {
                throw new IllegalLineException(String.format(ErrorMessage.METHOD_NOT_DEFINED_ERROR,
                        methodCall.getMethodName()));
            }
            methodCall.validateMethodCallParameters(methodSignatureToVariablesMap.get(methodCall.getMethodName()));
        }
    }


    /**
     * Validates the syntax of a single line of code.
     *
     * @param line The line of code to validate.
     * @throws IllegalLineException if the line contains a syntax error.
     */
    private static void validateLineSyntax(String line) throws IllegalLineException {
        line = line.trim(); // remove spaces from the start and end of line.
        if (lineIsIgnored(line) || validateScopeClosure(line)) {
            return;
        }
        if (returnWithoutCloseScope) {
            throw new IllegalLineException(ErrorMessage.CLOSED_AFTER_RETURN_ERROR_MSG);
        }
        if (scopesCollection.size() == 1) { // Line in global scope.
            checkLineValidityInGlobalScope(line); // check code in global scope
        } else if (scopesCollection.size() > 1) { // Line in method scope.
            checkLineValidityInMethodScope(line); // check code in method scope
        } else {
            throw new IllegalLineException(ErrorMessage.SYNTAX_ERROR_MSG);
        }

    }

    /**
     * This function checks the validity of a given line of code in the context of a method scope.
     * If the line is not a valid line of code, an IllegalLineException will be thrown.
     *
     * @param line The line of code to check for validity.
     * @throws IllegalLineException If the line is not a valid line of code.
     */
    private static void checkLineValidityInMethodScope(String line) throws IllegalLineException {
        if (!line.equals("}")) {
            endWithReturnStatement = false;
        }
        if (line.startsWith(Regex.RETURN_PREFIX)) {// Check if line starts with a return statement.
            checkReturnStatement(line);
        } else if (line.matches(Regex.METHOD_CALL_REGEX)) { // Check if line starts is a method call.
            methodCallsCollection.add(new MethodCall(line)); // Create a new method call and add it to the methodCallList.
            methodCallsCollection.get(methodCallsCollection.size() - 1).setScopeIndex(scopesCollection.size());
        } else if (line.matches(Regex.IF_WHILE_REGEX)) { // Check if/While statement
            scopesCollection.add(new Scope()); // Adds condition's scope to the scopes list.
            Condition newCondition = new Condition();
            newCondition.parsConditionLine(line);
        } else if (line.matches(Regex.VARIABLE_DECLARATION_REGEX)) { // Check variable declaration
            checkLineValidityForVariableDeclaration(line);
        } else { // Check variable assignment
            checkVariableAssignmentValidity(line);
        }
    }

    /**
     * This function checks the validity of a given line of code as a return statement.
     * If the line is not a valid return statement, an IllegalLineException will be thrown.
     *
     * @param line The line of code to check for validity as a return statement.
     * @throws IllegalLineException If the line is not a valid return statement.
     */
    private static void checkReturnStatement(String line) throws IllegalLineException {
        if (!line.matches(Regex.RETURN_STATEMENT_REGEX)) {
            throw new IllegalLineException(String.format(ErrorMessage.RETURN_STATEMENT_ERROR_MSG, line));
        }
        returnWithoutCloseScope = true;
        endWithReturnStatement = true;
    }

    /**
     * checkVariableAssignmentValidity verifies the validity of variable assignments in the method's scope.
     * It checks if the variables being assigned are declared and if the assignment type matches the variable type.
     * This function throws an exception if an invalid variable assignment is found.
     *
     * @param line The line of code to check for validity.
     * @throws IllegalLineException if an invalid variable assignment is found
     */
    private static void checkVariableAssignmentValidity(String line) throws IllegalLineException {
        if (!line.matches(Regex.ASSIGNMENT_EXPRESSION_REGEX)) {
            throw new IllegalLineException(String.format(ErrorMessage.INVALID_ASSIGNMENT_EXPRESSION_ERROR, line));
        }
        String[] parts = line.split(Regex.ASSIGNMENT_PREFIX);
        String variableName = parts[0].trim();
        String value = parts[1].trim();
        Variable variable = SjavaParser.searchVariableInScopes(variableName);
        if (variable == null || variable.isFinal()) {
            throw new IllegalLineException(String.format(ErrorMessage.VARIABLE_NOT_DEFINED_OR_FINAL_ERROR, variableName));
        }
        if (variable.getScopeIndex() == 1 && !variable.isInitializedInGlobalScope()) {
            throw new IllegalLineException(String.format(ErrorMessage.VARIABLE_NOT_INITIALIZED_IN_GLOBAL_SCOPE_ERROR,
                    variable.getName()));
        }
        variable.checkValueType(value, -1); // Check if value type is machine to declared variable type.
        variable.setInitialized(true);
    }


    /**
     * This function checks the validity of a given line of code in the global scope.
     * If the line is not a valid statement in the global scope, an IllegalLineException will be thrown.
     *
     * @param line The line of code to check for validity in the global scope.
     * @throws IllegalLineException If the line is not a valid statement in the global scope.
     */
    private static void checkLineValidityInGlobalScope(String line) throws IllegalLineException {
        if (!line.matches(Regex.WHITE_SPACE_CHECKER_REGEX)) { // If line like "int", throw exception.
            throw new IllegalLineException(ErrorMessage.SYNTAX_ERROR_MSG);
        }
        if (line.startsWith(Regex.VOID_PREFIX)) {
            checkLineValidityForMethodDeclaration(line); // Check if method declaration is valid.
        } else if (line.startsWith(Regex.FINAL_PREFIX) ||
                line.split(Regex.SPACE_REGEX)[0].matches(Regex.DATA_TAPES_REGEX)) {
            checkLineValidityForVariableDeclaration(line); // Check if validity declaration is valid.
        } else { // Check if line is an assignment expression (variableName = value;)
            Pattern pattern = Pattern.compile(Regex.VARIABLE_ASSIGNMENT_REGEX);
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                Variable variable = searchVariableInScopes(matcher.group(1));
                if (variable != null && variable.getType() == Type.getType(matcher.group(2))) {
                    return;
                }
            }
            throw new IllegalLineException(ErrorMessage.SYNTAX_ERROR_MSG);
        }
    }

    /**
     * This function checks the validity of the line for variable declaration.
     * If the line contains more than one variable declaration, the function will create a list of variables from the line.
     * The function will also check if the value type of the declared variable matches the type specified in the line.
     * The function will then check if the variable name is defined in the current scope.
     * If the variable is declared as final, the function will check if it is initialized.
     *
     * @param line the line to be checked for variable declaration validity.
     * @throws IllegalLineException if the line does not match the variable declaration syntax,
     *                              the variable name is already defined in the current scope,
     *                              or the value type of the declared variable does not match the type specified in the line.
     */
    private static void checkLineValidityForVariableDeclaration(String line) throws IllegalLineException {
        ArrayList<Variable> newVariableList = Factory.createVariableList(line); // Line can contain more than one variable declaration
        if (newVariableList.size() == 0) {
            throw new IllegalLineException(String.format(ErrorMessage.VARIABLE_DECLARATION_SYNTAX_ERROR, line));
        }
        Pattern pattern = Pattern.compile(Regex.VARIABLE_DECLARATION_PARSER_REGEX);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            newVariableList.get(0).checkValueType(matcher.group(), -1);
        }
        for (Variable variable : newVariableList) {
            variable.setScopeIndex(scopesCollection.size());
            checkIfVariableNameDefinedInScope(variable);
            if (variable.isFinal() && !variable.isInitialized()) {
                throw new IllegalLineException(String.format(ErrorMessage.VARIABLE_NAME_ALREADY_DEFINED_IN_SCOPE_ERROR,
                        variable.getName()));
            }
            if (variable.getScopeIndex() == 1 && variable.isInitialized()) {
                variable.setInitializedInGlobalScope(true);
            }
            scopesCollection.getLast().add(variable); // Add the variable to the current scope.
        }
    }

    /**
     * The checkLineValidityForMethodDeclaration method verifies if the method declaration line is valid, and if so,
     * creates a MethodDeclaration object and adds it to the methodSignatureToVariablesMap map.
     * If the method name already exists, an {@link IllegalLineException} is thrown.
     * This method also opens a new scope and adds the method parameters to it.
     *
     * @param line the line of code that contains the method declaration
     * @throws IllegalLineException if the method declaration line is invalid or if the method name already exists
     */
    private static void checkLineValidityForMethodDeclaration(String line) throws IllegalLineException {
        MethodDeclaration methodDeclaration = new MethodDeclaration(line);
        // Set function parameters to initialized
        methodDeclaration.getMethodParametersList().forEach(variable -> variable.setInitialized(true));
        // Check if the method's name already exists
        if (methodSignatureToVariablesMap.containsKey(methodDeclaration.getName())) {
            throw new IllegalLineException(String.format(ErrorMessage.DUPLICATE_METHOD_NAME_ERROR
                    , methodDeclaration.getName()));
        }
        methodSignatureToVariablesMap.put(methodDeclaration.getName(), methodDeclaration.getMethodParametersList());
        scopesCollection.add(new Scope()); // Open a new scope.
        methodDeclaration.getMethodParametersList().forEach(variable -> variable.setScopeIndex(scopesCollection.size()));
        for (Variable variable : methodDeclaration.getMethodParametersList()) {
            checkIfVariableNameDefinedInScope(variable);
            scopesCollection.getLast().add(variable);// Add all variables to the scope.
        }
    }

    /**
     * Verifies if the name of a {@link Variable} is already defined in the same scope.
     *
     * @param variable The {@link Variable} to be checked.
     * @throws IllegalLineException If the name of the {@link Variable} is already defined in the same scope.
     */
    private static void checkIfVariableNameDefinedInScope(Variable variable) throws IllegalLineException {
        Variable tmpVariable = searchVariableInScopes(variable.getName());
        if (tmpVariable != null && tmpVariable.getScopeIndex() == variable.getScopeIndex()) {
            throw new IllegalLineException(String.format(ErrorMessage.DUPLICATE_VARIABLE_NAME_ERROR, variable.getName()));
        }
    }

    /**
     * Validates the scope closure in a given line of code.
     *
     * @param line A line of code to be validated
     * @return true if the scope closure is valid, false otherwise
     * @throws IllegalLineException if the scope closure is invalid or an error occurs
     */
    private static boolean validateScopeClosure(String line) throws IllegalLineException {
        if (Pattern.matches(Regex.REGEX_SCOPE_CLOSURE, line)) {
            if (scopesCollection.size() <= 1) {
                throw new IllegalLineException(ErrorMessage.INVALID_SCOPES_SYNTAX_MSG);
            }
            scopesCache.add(scopesCollection.getLast()); // Save the last scope in the cache.
            scopesCollection.removeLast(); // Closings the least scope.
            if (!endWithReturnStatement && scopesCollection.size() == 1) {
                throw new IllegalLineException(ErrorMessage.INVALID_SCOPES_SYNTAX_ERROR);
            }
            returnWithoutCloseScope = false;
            endWithReturnStatement = false;
            return true;
        }
        return false;
    }

    /**
     * Returns true if the input line is ignored, otherwise false.
     * A line is considered ignored if it's empty or starts with a comment prefix as
     * defined by {@link Regex#COMMENT_PREFIX}.
     * Before checking for these conditions, this function validates the input
     * line with {@link #isIllegalCommentLine(String)}.
     *
     * @param line the input line to be checked.
     * @return true if the line is ignored, false otherwise.
     * @throws IllegalLineException if the input line is an illegal comment line.
     */
    private static boolean lineIsIgnored(String line) throws IllegalLineException {
        isIllegalCommentLine(line);
        return line.isEmpty() || line.startsWith(Regex.COMMENT_PREFIX);
    }

    /**
     * Validates if the input line is an illegal comment line.
     * A line is considered illegal if it matches the comment regex as defined by {@link Regex#COMMENT_REGEX}
     * but doesn't start with a comment prefix as defined by {@link Regex#COMMENT_PREFIX}.
     *
     * @param line the input line to be validated.
     * @throws IllegalLineException if the input line is an illegal comment line.
     */
    private static void isIllegalCommentLine(String line) throws IllegalLineException {
        if (Pattern.matches(Regex.COMMENT_REGEX, line) && !line.startsWith(Regex.COMMENT_PREFIX)) {
            throw new IllegalLineException(ErrorMessage.ILLEGAL_COMMENT_LINE_ERROR_MSG);
        }
    }

    /**
     * Searches for a variable with the given name in all scopes in the {@link #scopesCollection}.
     * The search is done by mapping the {@link Scope#getNameToVariableHashMap()} of each scope
     * in the {@link #scopesCollection}
     * and retrieving the variable with the given name. The first non-null result is returned.
     * If no such variable is found, returns null.
     *
     * @param parameterName the name of the variable to search for.
     * @return the first variable with the given name found in the {@link #scopesCollection}, or null if not found.
     */
    public static Variable searchVariableInScopes(String parameterName) {
        return scopesCollection.stream().map(Scope::getNameToVariableHashMap)
                .map(map -> map.get(parameterName))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

    /**
     * Searches for a variable with the given name in the scopes cache in {@link #scopesCache}.
     * The search is done by mapping the {@link Scope#getNameToVariableHashMap()} of each scope
     * in the {@link #scopesCache}
     * and retrieving the variable with the given name. The first non-null result is returned.
     * If no such variable is found, returns null.
     *
     * @param parameterName the name of the variable to search for.
     * @return the first variable with the given name found in the {@link #scopesCache}, or null if not found.
     */
    public static Variable searchVariableInScopesCache(String parameterName) {
        return scopesCache.stream().map(Scope::getNameToVariableHashMap)
                .map(map -> map.get(parameterName))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }
}