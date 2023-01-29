package oop.ex6.errorMessage;

public class ErrorMessage {
    public static final String FILE_NOT_FOUND_ERROR_MSG = "File not found";
    public static final String ILLEGAL_COMMENT_LINE_ERROR_MSG = "Illegal comment line." +
            " Comment line most to start with '//'.";
    public static final String CLOSED_AFTER_RETURN_ERROR_MSG = "ERROR: Scope not closed after return statement." +
            " All scopes must be closed after a return statement.";
    public static final String INVALID_SCOPES_SYNTAX_MSG = "ERROR: Invalid scope closing syntax";
    public static final String SYNTAX_ERROR_MSG = "ERROR: Syntax error";
    public static final String UNTERMINATED_GLOBAL_SCOPE_MSG = "ERROR: The code must be terminated with a '}' " +
            "in the global scope to indicate the end of the program.";
    public static final String METHOD_NOT_DEFINED_ERROR = "ERROR: The method '%s' is not defined in this scope.";
    public static final String RETURN_STATEMENT_ERROR_MSG = "ERROR: Illegal return statement '%s'. Should be 'return;'.";
    public static final String INVALID_ASSIGNMENT_EXPRESSION_ERROR = "ERROR: Invalid assignment expression: '%s'. " +
            "Assignment expressions must end with a semicolon (;) and include an assignment operator (=).";
    public static final String VARIABLE_NOT_DEFINED_OR_FINAL_ERROR = "ERROR: The variable '%s' is either not defined" +
            " or is a final variable, therefore it cannot be reassigned.";
    public static final String VARIABLE_NOT_INITIALIZED_IN_GLOBAL_SCOPE_ERROR = "ERROR: Variable '%s' must be" +
            " initialized in the global scope before being used in the current scope.";
    public static final String VARIABLE_DECLARATION_SYNTAX_ERROR = "ERROR: The syntax used for declaring the variable " +
            "is incorrect (%s).";
    public static final String VARIABLE_NAME_ALREADY_DEFINED_IN_SCOPE_ERROR = "ERROR: Cannot declare a final variable" +
            " without initializing it (variable name: %s).";
    public static final String DUPLICATE_METHOD_NAME_ERROR = "ERROR: A method with the same name already exists (%s).";
    public static final String INVALID_SCOPES_SYNTAX_ERROR = "ERROR: Missing return statement at end of method or block.";
    public static final String DUPLICATE_VARIABLE_NAME_ERROR = "ERROR: Variable '%s' already defined in this scope.";

    public static final String INCORRECT_CONDITION_LINE_SYNTAX_ERROR = "ERROR: The syntax of the condition line " +
            "is incorrect (%s).";
    public static final String EMPTY_CONDITION_ERROR = "ERROR: Illegal empty condition statement '%s'.";
    public static final String VARIABLE_NOT_INITIALIZED_OR_DECLARED_ERROR = "ERROR: The parameter ('%s') used in the" +
            " condition has not been decelerate or initialized.";
    public static final String UNSUPPORTED_PARAMETER_TYPE_ERROR = "ERROR: The parameter's type is not supported (%s).";
    public static final String INVALID_METHOD_NAME_ERROR = "ERROR: Invalid method call: '%s'. The method name must " +
            "start with a letter and can only contain letters, numbers, and underscores.";
    public static final String INVALID_PARAMETER_NUMBER_ERROR = "ERROR: Number of parameters passed in method call " +
            "does not match the number of parameters in method declaration. Expected %d but got %d.";
    public static final String INVALID_SYNTAX_FOR_METHOD_DECLARATION = "ERROR: Invalid syntax for method declaration (%s).";
    public static final String INVALID_SYNTAX_FOR_METHOD_NAME_ERROR = "ERROR: Invalid syntax for method's name (%s).";
    public static final String INVALID_PARAMETER_INITIALIZATION_IN_METHOD_DEFINITION = "ERROR: The initialization of" +
            " a parameter within the method definition is invalid.";
    public static final String INVALID_SYNTAX_FOR_VARIABLE_DECLARATION = "ERROR: Invalid syntax for variable " +
            "declaration (%s). Use the correct format: [Type] [Variable Name]";
    public static final String INVALID_SYNTAX_FOR_VARIABLE_DECLARATION_OR_ASSIGNMENT = "ERROR: Invalid syntax for" +
            " variable declaration or assignment (%s).";

    public static final String UNSUPPORTED_TYPE_ERROR = "ERROR: Unsupported type";
    public static final String INCOMPATIBLE_TYPE_ERROR = "ERROR: Incompatible types, expected %s but got %s.";
    public static final String ILLEGAL_USAGE_ERROR = "ERROR: Illegal usage. Please check your path to the Sjava file.";
}
