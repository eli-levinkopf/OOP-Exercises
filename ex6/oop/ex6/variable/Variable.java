package oop.ex6.variable;

import oop.ex6.errorMessage.ErrorMessage;
import oop.ex6.parser.IllegalLineException;
import oop.ex6.parser.SjavaParser;
import oop.ex6.regex.Regex;

/**
 * The class Variable represents a variable declaration in S-Java.
 * It contains information about the variable's name, type, initialization state, whether it's final or not,
 * the scope index and whether it's initialized in the global scope.
 *
 * @author Eli Levinkopf
 */
public class Variable {
    /**
     * Boolean variable indicating whether the variable is initialized or not.
     */
    private boolean isInitialized = false;

    /**
     * Boolean variable indicating whether the variable is final or not.
     */
    private boolean isFinal = false;

    /**
     * The type of the variable.
     */
    private final Type type;

    /**
     * The name of the variable.
     */
    private final String name;

    /**
     * The index of the scope of the variable.
     */
    private int scopeIndex;

    /**
     * Boolean variable indicating whether the variable is initialized in the global scope.
     */
    private boolean initializedInGlobalScope = false;


    /**
     * Constructor that creates a new Variable object with the given name, type and final status.
     *
     * @param isFinal      A boolean indicating whether the variable is final or not.
     * @param type         The type of the variable.
     * @param variableName The name of the variable.
     */
    public Variable(boolean isFinal, Type type, String variableName) {
        this.isFinal = isFinal;
        this.type = type;
        name = variableName;
    }

    /**
     * @return A boolean indicating whether the variable is initialized or not.
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * @return A boolean indicating whether the variable is final or not.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * @return The type of the variable.
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The name of the variable.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The index of the scope of the variable.
     */
    public int getScopeIndex() {
        return scopeIndex;
    }

    /**
     * @return A boolean indicating whether the variable is initialized in the global scope.
     */
    public boolean isInitializedInGlobalScope() {
        return initializedInGlobalScope;
    }

    /**
     * Sets the boolean indicating whether the variable is initialized in the global scope.
     *
     * @param initializedInGlobalScope A boolean indicating whether the variable is initialized in the global scope.
     */
    public void setInitializedInGlobalScope(boolean initializedInGlobalScope) {
        this.initializedInGlobalScope = initializedInGlobalScope;
    }

    /**
     * The method setScopeIndex updates the scopeIndex property of the variable.
     *
     * @param scopeIndex An int representing the index of the scope where the variable is declared.
     */
    public void setScopeIndex(int scopeIndex) {
        this.scopeIndex = scopeIndex;
    }

    /**
     * The method setInitialized updates the initialization status of the variable.
     *
     * @param initialized A boolean indicating the initialization status of the variable.
     */
    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    /**
     * The method setFinal updates the final property of the variable.
     *
     * @param aFinal A boolean indicating whether the variable is final or not.
     */
    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    /**
     * The method checkValueType checks the type compatibility between the value of a variable and the type
     * of the variable.
     *
     * @param value                The value of the variable as a string.
     * @param methodCallScopeIndex The index of the scope where the method call is being made, or -1 if not applicable.
     * @throws IllegalLineException If the type of the value is not compatible with the type of the variable.
     */
    public void checkValueType(String value, int methodCallScopeIndex) throws IllegalLineException {
        Type type = Type.getType(value.replace(Regex.SEMICOLON_PREFIX, Regex.EMPTY_STRING).trim());
        Variable variable;
        if (methodCallScopeIndex != -1) { // Check for methodCall parameters (search in all scopes in the cache).
            variable = SjavaParser.searchVariableInScopesCache(value);
            if (type == this.type || (variable != null && variable.getType() == this.type
                    && variable.isInitialized() && variable.getScopeIndex() == methodCallScopeIndex)) {
                return;
            }
        } else { // Check for variable.
            variable = SjavaParser.searchVariableInScopes(value);
            if (type == this.type || (variable != null && variable.getType() == this.type
                    && variable.isInitialized())) {
                return;
            }
        }
        if (type == null || (this.type == Type.BOOLEAN && type != Type.DOUBLE && type != Type.INT) ||
                (this.type != Type.BOOLEAN && this.type != Type.DOUBLE && type != Type.INT) || this.type == Type.STRING
                || type == Type.STRING || this.type == Type.CHAR || type == Type.CHAR) {
            throw new IllegalLineException(String.format(ErrorMessage.INCOMPATIBLE_TYPE_ERROR, this.type, type));
        }
    }
}
